package com.financeapp.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.financeapp.android.data.database.FinanceDatabase
import com.financeapp.android.data.model.RecurringFrequency
import com.financeapp.android.data.model.Transaction
import com.financeapp.android.data.model.TransactionType
import java.util.Calendar

class RecurringTransactionWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val database = FinanceDatabase.getDatabase(applicationContext)
            val transactionDao = database.transactionDao()
            
            val recurringTransactions = transactionDao.getRecurringTransactions()
            
            // Collect the flow (in a real app, you'd handle this differently)
            // For now, we'll just return success
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun shouldCreateTransaction(today: Calendar, lastCreated: Long?, frequency: RecurringFrequency): Boolean {
        if (lastCreated == null) return true
        
        val lastCreatedCalendar = Calendar.getInstance().apply { timeInMillis = lastCreated }
        
        return when (frequency) {
            RecurringFrequency.DAILY -> {
                today.get(Calendar.DAY_OF_YEAR) != lastCreatedCalendar.get(Calendar.DAY_OF_YEAR)
            }
            RecurringFrequency.WEEKLY -> {
                today.get(Calendar.WEEK_OF_YEAR) != lastCreatedCalendar.get(Calendar.WEEK_OF_YEAR)
            }
            RecurringFrequency.MONTHLY -> {
                today.get(Calendar.MONTH) != lastCreatedCalendar.get(Calendar.MONTH) ||
                today.get(Calendar.YEAR) != lastCreatedCalendar.get(Calendar.YEAR)
            }
            RecurringFrequency.YEARLY -> {
                today.get(Calendar.YEAR) != lastCreatedCalendar.get(Calendar.YEAR)
            }
        }
    }

    private fun getNextOccurrence(frequency: RecurringFrequency): Long {
        val calendar = Calendar.getInstance()
        return when (frequency) {
            RecurringFrequency.DAILY -> calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis
            RecurringFrequency.WEEKLY -> calendar.apply { add(Calendar.WEEK_OF_YEAR, 1) }.timeInMillis
            RecurringFrequency.MONTHLY -> calendar.apply { add(Calendar.MONTH, 1) }.timeInMillis
            RecurringFrequency.YEARLY -> calendar.apply { add(Calendar.YEAR, 1) }.timeInMillis
        }
    }
}
