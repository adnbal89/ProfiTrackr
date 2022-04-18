package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import javax.inject.Inject

class InsertStockTradeUseCase @Inject constructor(
    private val stockTradeRepository: StockTradeRepository
) : UseCase<None, StockTrade>() {

    override suspend fun run(params: StockTrade): Either<Failure, None> =
        stockTradeRepository.insertStockTradeToDatabase(stockTrade = params)


}