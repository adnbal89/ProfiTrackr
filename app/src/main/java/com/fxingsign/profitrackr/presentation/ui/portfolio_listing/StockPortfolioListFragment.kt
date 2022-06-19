package com.fxingsign.profitrackr.presentation.ui.portfolio_listing

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockListBinding
import com.fxingsign.profitrackr.domain.SortOrder
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.presentation.ui.adapters.StockPortfolioListAdapter
import com.fxingsign.profitrackr.util.failure
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.functional.exhaustive
import com.fxingsign.profitrackr.util.observe
import com.fxingsign.profitrackr.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StockPortfolioListFragment : Fragment(R.layout.fragment_stock_list) {
    val TAG = "StockPortfolioListFragment"
    private val viewModel: StockPortfolioListViewModel by viewModels()

    private lateinit var searchView: SearchView

    private val stockPortfolioListAdapter = StockPortfolioListAdapter(
        onItemClick = { stockPortfolio ->
            viewModel.onStockClicked(stockPortfolio.symbol)
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

        //initial data load
        loadStockPortfolioList("")

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stocksEvent.collect { event ->

                //.exhaustive: for runtime safety
                when (event) {
                    StockPortfolioListViewModel.StocksEvent.NavigateToAddStockTradeScreen -> {
                        val action =
                            StockPortfolioListFragmentDirections.actionStockPortfolioListFragmentToStockAddEditTradeFragment(
                                "add",
                                getString(R.string.add_new_stock_trade),
                                null
                            )
                        findNavController().navigate(action)
                    }
                    is StockPortfolioListViewModel.StocksEvent.NavigateToStockHistoryScreen -> {
                        val action =
                            StockPortfolioListFragmentDirections.actionStockPortfolioListFragmentToStockTradeHistoryFragment(
                                event.stockId,
                                getString(R.string.stock_trade_history)
                            )
                        findNavController().navigate(action)
                    }
                    is StockPortfolioListViewModel.StocksEvent.ShowTaskSavedConfirmationMessage -> TODO()
                    is StockPortfolioListViewModel.StocksEvent.ShowUndoDeleteTaskMessage -> TODO()
                    is StockPortfolioListViewModel.StocksEvent.NavigateToEditStockTradeScreen -> TODO()
                }.exhaustive
            }
        }

        setHasOptionsMenu(true);
    }

    private fun renderStockPortfolioList(stockPortfolioList: List<StockPortfolio>?) {
        stockPortfolioListAdapter.submitList(stockPortfolioList)
    }

    private fun handleFailure(failure: Failure?) {

    }

    private fun loadStockPortfolioList(searchTerm: String) {
        viewModel.getStockPortfolioList(searchTerm)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_stock_portfolio_list, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value

        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged { query ->
            viewModel.searchQuery.value = query
            loadStockPortfolioList(query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.searchQuery.value?.let { loadStockPortfolioList(it) }
                true
            }
            R.id.action_sort_by_date_created -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_add_stock -> {
                viewModel.onAddNewStockTradeClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchView.setOnQueryTextListener(null)
    }

}