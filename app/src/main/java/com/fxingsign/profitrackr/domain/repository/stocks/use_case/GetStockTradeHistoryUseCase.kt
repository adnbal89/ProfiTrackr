package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockTradeHistoryUseCase @Inject constructor(
    private val stockTradeRepository: StockTradeRepository
) : UseCase<Flow<List<StockTrade>>, None>() {

    override suspend fun run(params: None): Either<Failure, Flow<List<StockTrade>>> {

        val stockTradeHistory = stockTradeRepository.getStockTradeHistory()
        return stockTradeHistory
    }

}