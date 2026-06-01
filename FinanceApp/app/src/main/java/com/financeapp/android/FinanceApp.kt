package com.financeapp.android

import android.app.Application
import androidx.work.*
import com.financeapp.android.data.database.FinanceDatabase
import com.financeapp.android.data.model.RecurringFrequency
import com.financeapp.android.data.model.TransactionType
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class FinanceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize database
        val database = FinanceDatabase.getDatabase(this)
        
        // Schedule recurring transaction worker
        scheduleRecurringTransactions()
    }

    private fun scheduleRecurringTransactions() {
        val workRequest = PeriodicWorkRequestBuilder<RecurringTransactionWorker>(
            1, TimeUnit.DAYS
        )
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
        )
        .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recurring_transactions",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
