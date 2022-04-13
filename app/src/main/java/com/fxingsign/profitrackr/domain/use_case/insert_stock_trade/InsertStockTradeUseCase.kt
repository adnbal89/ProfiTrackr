package com.fxingsign.profitrackr.domain.use_case.insert_stock_trade

import com.fxingsign.profitrackr.domain.repository.StockTradeRepository
import javax.inject.Inject

class InsertStockTradeUseCase @Inject constructor(
    private val repository: StockTradeRepository
) {
}