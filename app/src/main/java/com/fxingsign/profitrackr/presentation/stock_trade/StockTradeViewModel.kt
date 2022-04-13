package com.fxingsign.profitrackr.presentation.stock_trade

import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.InsertStockTradeUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockTradeViewModel @Inject constructor(
    private val insertStockTradeUseCase: InsertStockTradeUseCase
) : BaseViewModel() {

    fun insertStockTrade(stockTrade: StockTrade) {
        insertStockTradeUseCase(params = stockTrade)
    }
}