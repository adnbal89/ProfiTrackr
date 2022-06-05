package com.fxingsign.profitrackr.presentation.ui.stock_trade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockTradeHistoryUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import com.fxingsign.profitrackr.presentation.ui.portfolio_listing.StockPortfolioListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockTradeHistoryViewModel @Inject constructor(
    private val getStockTradeHistoryUseCase: GetStockTradeHistoryUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {
    val TAG = "StockTradeHistoryViewModel"

    //get the object from nav_graph ,
    // argument has to be the same as the nav_Graph -> argument name
    val stockId = state.get<String>("stockId")


    private val _stockTrades: MutableLiveData<List<StockTrade>> = MutableLiveData()
    val stockTrades: LiveData<List<StockTrade>> = _stockTrades

    //channel instantiation. carries TasksEvent
    //if we expose a channel to the outside, then consumer fragment can put something in it. we do not want that.
    //to not to expose channel outside, we create tasksEvent val.
    private val stocksEventChannel = Channel<StockHistoryEvent>()

    //basically turns this channel into a flow that we can than the fragment can use the single values out of it.
    val stocksEvent = stocksEventChannel.receiveAsFlow()

    fun getStockTradeHistoryById(stockId: String) {
        return getStockTradeHistoryUseCase(stockId, viewModelScope) {
            it.fold(::handleFailure, ::handleStockTradeList)
        }
    }

    private fun handleStockTradeList(stockTrades: Flow<List<StockTrade>>) {

        viewModelScope.launch {
            stockTrades.collect {
                _stockTrades.value = it
            }
        }
    }

    fun onStockTradeClicked(stockTrade: StockTrade) =
        viewModelScope.launch {
            stocksEventChannel.send(StockHistoryEvent.NavigateToEditStockTradeScreen(stockTrade))
        }

    sealed class StockHistoryEvent {
        data class ShowInvalidInputMessage(val msg: String) : StockHistoryEvent()
        data class NavigateBackWithResult(val result: String) : StockHistoryEvent()
        data class NavigateBackWithStockResult(val result: String) : StockHistoryEvent()
        data class NavigateToEditStockTradeScreen(val stockTrade: StockTrade) : StockHistoryEvent()
    }
}