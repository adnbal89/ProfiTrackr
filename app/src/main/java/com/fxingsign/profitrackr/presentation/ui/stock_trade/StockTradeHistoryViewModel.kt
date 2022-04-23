package com.fxingsign.profitrackr.presentation.ui.stock_trade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockTradeHistoryUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

    sealed class StockHistoryEvent {
        data class ShowInvalidInputMessage(val msg: String) : StockHistoryEvent()
        data class NavigateBackWithResult(val result: String) : StockHistoryEvent()
        data class NavigateBackWithStockResult(val result: String) : StockHistoryEvent()
    }
}