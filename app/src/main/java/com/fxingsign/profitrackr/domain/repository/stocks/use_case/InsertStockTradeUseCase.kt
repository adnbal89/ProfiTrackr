package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import javax.inject.Inject

class InsertStockTradeUseCase @Inject constructor(
    private val stockTradeRepository: StockTradeRepository
) : UseCase<None, StockTradeDto>() {

    override suspend fun run(params: StockTradeDto): Either<Failure, None> =
        stockTradeRepository.insertStockTradeToDatabase(stockTradeDto = params)


}