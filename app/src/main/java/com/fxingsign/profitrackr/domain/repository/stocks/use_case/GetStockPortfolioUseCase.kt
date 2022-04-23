package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockPortfolioRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockPortfolioUseCase @Inject constructor(
    private val stockPortfolioRepository: StockPortfolioRepository
) : UseCase<Flow<List<StockPortfolio>>, String>() {

    override suspend fun run(params: String): Either<Failure, Flow<List<StockPortfolio>>> {
        return stockPortfolioRepository.getStockPortfolio(params)
    }
}
