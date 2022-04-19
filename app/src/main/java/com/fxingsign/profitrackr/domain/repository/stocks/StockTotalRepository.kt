package com.fxingsign.profitrackr.domain.repository.stocks

import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTotal
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow

interface StockTotalRepository {

    suspend fun getStockPortfolio()
            : Either<Failure, Flow<List<StockTotal>>>

}