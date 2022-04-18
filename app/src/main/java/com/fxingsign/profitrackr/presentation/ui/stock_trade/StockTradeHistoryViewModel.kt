package com.fxingsign.profitrackr.presentation.ui.stock_trade

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockTradeHistoryUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockTradeHistoryViewModel @Inject constructor(
    private val getStockTradeHistoryUseCase: GetStockTradeHistoryUseCase
) : BaseViewModel() {
    val TAG = "StockTradeHistoryViewModel"

    private val _stockTrades: MutableLiveData<List<StockTrade>> = MutableLiveData()
    val stockTrades: LiveData<List<StockTrade>> = _stockTrades

    fun getStockTradeHistory() {
        return getStockTradeHistoryUseCase(None(), viewModelScope) {
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
}