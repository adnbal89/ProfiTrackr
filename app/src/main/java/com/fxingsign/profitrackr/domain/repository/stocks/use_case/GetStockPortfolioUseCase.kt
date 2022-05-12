package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockPortfolioRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockPortfolioUseCase @Inject constructor(
    private val stockPortfolioRepository: StockPortfolioRepository
) : UseCase<Flow<List<StockPortfolio>>, GetStockPortfolioUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Flow<List<StockPortfolio>>> {
        return stockPortfolioRepository.getStockPortfolio(params.query)
    }

    data class Params(val query: String)

}
