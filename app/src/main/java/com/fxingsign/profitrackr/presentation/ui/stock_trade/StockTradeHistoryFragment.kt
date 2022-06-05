package com.fxingsign.profitrackr.presentation.ui.stock_trade

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockTradeHistoryBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.presentation.ui.adapters.StockTradeHistoryListAdapter
import com.fxingsign.profitrackr.presentation.ui.add_edit_stock.StockAddEditTradeViewModel
import com.fxingsign.profitrackr.util.failure
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StockTradeHistoryFragment : Fragment(R.layout.fragment_stock_trade_history) {
    val TAG = "StockTradeHistoryFragment"
    private val viewModel: StockTradeHistoryViewModel by viewModels()
    private val viewModel2: StockAddEditTradeViewModel by viewModels()

    private val stockTradeHistoryAdapter = StockTradeHistoryListAdapter(
        onItemClick = { stockTrade ->
            viewModel.onStockTradeClicked(stockTrade)
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stocksEvent.collect { event ->
                when (event) {
                    is StockTradeHistoryViewModel.StockHistoryEvent.NavigateBackWithResult -> TODO()
                    is StockTradeHistoryViewModel.StockHistoryEvent.NavigateBackWithStockResult -> TODO()
                    is StockTradeHistoryViewModel.StockHistoryEvent.NavigateToEditStockTradeScreen -> {
                        val action =
                            StockTradeHistoryFragmentDirections.actionStockTradeHistoryFragmentToStockAddEditTradeFragment(
                                "edit",
                                getString(R.string.edit_stock_trade),
                                stockTrade = event.stockTrade
                            )
                        findNavController().navigate(action)
                    }
                    is StockTradeHistoryViewModel.StockHistoryEvent.ShowInvalidInputMessage -> TODO()
                }
            }
        }

        binding.apply {
            recyclerViewStockTrades.apply {
                adapter = stockTradeHistoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            textViewStockName.text = viewModel.stockId
        }

        //insertCoupleOfDbItems()

        loadStockTradeHistory(viewModel.stockId ?: "")
    }

    private fun loadStockTradeHistory(stockId: String) {
        viewModel.getStockTradeHistoryById(stockId)
    }

    private fun renderStockTradeHistoryList(stockTradeHistory: List<StockTrade>?) {
        stockTradeHistoryAdapter.submitList(stockTradeHistory)
    }

    private fun handleFailure(failure: Failure?) {

    }

    /*private fun insertCoupleOfDbItems() {
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "ASELS",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "ISGYO",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "ISCTR",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "sell"
            )
        )
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "KARTN",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "buy"
            )
        )
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "YKBNK",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "sell"
            )
        )
        viewModel2.insertStockTrade(
            StockTradeDto(
                symbol = "GARAN",
                quantity = 1500,
                buyPrice = 23.5,
                date = "14.04.2022",
                tradeType = "buy"
            )
        )
    }*/

}