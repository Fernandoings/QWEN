package com.financeapp.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.financeapp.android.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupObservers()
        setupSwipeRefresh()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.totalBalance.collectLatest { balance ->
                binding.tvTotalBalance.text = formatCurrency(balance ?: 0.0)
            }
        }
        
        lifecycleScope.launch {
            viewModel.monthlyIncome.collectLatest { income ->
                binding.tvMonthlyIncome.text = formatCurrency(income ?: 0.0)
            }
        }
        
        lifecycleScope.launch {
            viewModel.monthlyExpenses.collectLatest { expenses ->
                binding.tvMonthlyExpenses.text = formatCurrency(expenses ?: 0.0)
            }
        }
        
        lifecycleScope.launch {
            viewModel.recentTransactions.collectLatest { transactions ->
                // Update RecyclerView with recent transactions
            }
        }
        
        lifecycleScope.launch {
            viewModel.activeBudgets.collectLatest { budgets ->
                // Update budget progress indicators
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun formatCurrency(amount: Double): String {
        return "$ ${String.format("%.2f", amount)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
