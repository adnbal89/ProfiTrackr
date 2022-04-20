package com.fxingsign.profitrackr.presentation.ui.portfolio_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockListBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.presentation.ui.stock_trade.adapters.StockPortfolioListAdapter
import com.fxingsign.profitrackr.util.failure
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockPortfolioListFragment : Fragment(R.layout.fragment_stock_list) {
    val TAG = "StockPortfolioListFragment"
    private val viewModel: StockPortfolioListViewModel by viewModels()

    private val stockPortfolioListAdapter = StockPortfolioListAdapter(
        onItemClick = { stockPortfolio ->
            Snackbar.make(
                requireView(), "StockPortfolio Item Clicked",
                Snackbar.LENGTH_LONG
            ).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {
            observe(stockPortfolioList, ::renderStockPortfolioList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStockListBinding.bind(view)
        binding.apply {
            recyclerViewPortfolio.apply {
                adapter = stockPortfolioListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        loadStockPortfolioList()
    }

    private fun renderStockPortfolioList(stockPortfolioList: List<StockPortfolio>?) {
        stockPortfolioListAdapter.submitList(stockPortfolioList)
    }

    private fun handleFailure(failure: Failure?) {

    }

    private fun loadStockPortfolioList() {
        viewModel.getStockPortfolioList()
    }
}