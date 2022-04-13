package com.fxingsign.profitrackr.domain.repository.stocks

import com.fxingsign.profitrackr.domain.functional.Either
import com.fxingsign.profitrackr.domain.functional.exception.Failure
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None

interface StockTradeRepository {

    suspend fun insertStockTradeToDatabase(stockTrade: StockTrade): Either<Failure, None>
}