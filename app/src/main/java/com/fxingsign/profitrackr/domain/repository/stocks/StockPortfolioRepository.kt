package com.fxingsign.profitrackr.domain.repository.stocks

import com.fxingsign.profitrackr.domain.SortOrder
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow

interface StockPortfolioRepository {

    suspend fun getStockPortfolio(searchTerm: String)
            : Either<Failure, Flow<List<StockPortfolio>>>

}