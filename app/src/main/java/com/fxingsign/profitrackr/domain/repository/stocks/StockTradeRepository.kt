package com.fxingsign.profitrackr.domain.repository.stocks

import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow

interface StockTradeRepository {

    suspend fun insertStockTradeToDatabase(
        stockTradeDto: StockTradeDto
    ): Either<Failure, None>

    fun getStockTradeHistory()
            : Either<Failure, Flow<List<StockTrade>>>
}