package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockTradeHistoryUseCase @Inject constructor(
    private val stockTradeRepository: StockTradeRepository
) : UseCase<Flow<List<StockTrade>>, String>() {

    override suspend fun run(params: String): Either<Failure, Flow<List<StockTrade>>> {
        return stockTradeRepository.getStockTradeHistoryById(params)
    }

}