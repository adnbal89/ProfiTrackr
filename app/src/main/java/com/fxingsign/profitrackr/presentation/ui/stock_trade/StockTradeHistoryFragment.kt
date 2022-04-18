package com.fxingsign.profitrackr.presentation.ui.stock_trade

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockTradeHistoryBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.presentation.ui.stock_trade.adapters.StockTradeHistoryListAdapter
import com.fxingsign.profitrackr.util.failure
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockTradeHistoryFragment : Fragment(R.layout.fragment_stock_trade_history) {
    val TAG = "StockTradeHistoryFragment"
    private val viewModel: StockTradeHistoryViewModel by viewModels()
    private val viewModel2: StockTradeViewModel by viewModels()

    private val stockTradeHistoryAdapter = StockTradeHistoryListAdapter(
        onItemClick = { stockTrade ->
            Snackbar.make(
                requireView(), "StockTrade Clicked",
                Snackbar.LENGTH_LONG
            ).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {
            observe(stockTrades, ::renderStockTradeHistoryList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStockTradeHistoryBinding.bind(view)

        binding.apply {
            recyclerViewStockTrades.apply {
                adapter = stockTradeHistoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        insertCoupleOfDbItems()

        loadStockTradeHistory()
    }

    private fun loadStockTradeHistory() {
        viewModel.getStockTradeHistory()
    }

    private fun renderStockTradeHistoryList(stockTradeHistory: List<StockTrade>?) {
        stockTradeHistoryAdapter.submitList(stockTradeHistory)
    }

    private fun handleFailure(failure: Failure?) {

    }

    private fun insertCoupleOfDbItems() {
        viewModel2.insertStockTrade(
            StockTrade(
                "ASELS",
                1500,
                23.5,
                "14.04.2022",
                "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTrade(
                "ISGYO",
                1500,
                23.5,
                "14.04.2022",
                "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTrade(
                "ISCTR",
                1500,
                23.5,
                "14.04.2022",
                "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTrade(
                "KARTN",
                1500,
                23.5,
                "14.04.2022",
                "sell"
            )
        )
        viewModel2.insertStockTrade(
            StockTrade(
                "YKBNK",
                1500,
                23.5,
                "14.04.2022",
                "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTrade(
                "GARAN",
                1500,
                23.5,
                "14.04.2022",
                "sell"
            )
        )
    }

}