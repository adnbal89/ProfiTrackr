package com.fxingsign.profitrackr.presentation.ui.portfolio_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.PreferencesManager
import com.fxingsign.profitrackr.domain.SortOrder
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockPortfolioUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockPortfolioUseCase.Params
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockPortfolioListViewModel @Inject constructor(
    private val getStockPortfolioUseCase: GetStockPortfolioUseCase,
    private val preferencesManager: PreferencesManager,
    private val state: SavedStateHandle
) : BaseViewModel() {
    val TAG = "StockPortfolioListViewModel"

    private val _stockPortfolioList: MutableLiveData<List<StockPortfolio>> = MutableLiveData()
    val stockPortfolioList: LiveData<List<StockPortfolio>> = _stockPortfolioList

    val searchQuery = state.getLiveData("searchQuery", "")

    //channel instantiation. carries TasksEvent
    //if we expose a channel to the outside, then consumer fragment can put something in it. we do not want that.
    //to not to expose channel outside, we create tasksEvent val.
    private val stocksEventChannel = Channel<StocksEvent>()

    //basically turns this channel into a flow that we can than the fragment can use the single values out of it.
    val stocksEvent = stocksEventChannel.receiveAsFlow()


    fun getStockPortfolioList(searchTerm: String) =
        getStockPortfolioUseCase(Params(searchTerm), viewModelScope) {
            it.fold(::handleFailure, ::handleStockPortfolioList)
        }


    private fun handleStockPortfolioList(stockPortfolioList: Flow<List<StockPortfolio>>) {
        viewModelScope.launch {
            stockPortfolioList.collect {
                _stockPortfolioList.value = it
            }
        }
    }

    fun onAddNewStockTradeClicked() =
        viewModelScope.launch {
            stocksEventChannel.send(StocksEvent.NavigateToAddStockTradeScreen)
        }

    fun onStockClicked(stockId: String,stockPortfolio: StockPortfolio) =
        viewModelScope.launch {
            //stocksEventChannel.send(StocksEvent.NavigateToStockHistoryScreen(stockId))
            stocksEventChannel.send(StocksEvent.NavigateToEditStockTradeScreen(stockPortfolio))
        }

    fun filterStockList(searchTerm: String) {
        var temp = _stockPortfolioList.value
        _stockPortfolioList.value = _stockPortfolioList.value?.filter {
            it.symbol.contains(searchTerm, ignoreCase = true)
        }
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    //to represent different kinds of events to be able to send to the fragment.
    sealed class StocksEvent {
        object NavigateToAddStockTradeScreen : StocksEvent()
        //TODO:change to stockTrade
        data class NavigateToEditStockTradeScreen(val stockPortfolio: StockPortfolio) : StocksEvent()
        data class NavigateToStockHistoryScreen(val stockId: String) : StocksEvent()
        data class ShowUndoDeleteTaskMessage(val stockTrade: StockTrade) : StocksEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : StocksEvent()
    }


}