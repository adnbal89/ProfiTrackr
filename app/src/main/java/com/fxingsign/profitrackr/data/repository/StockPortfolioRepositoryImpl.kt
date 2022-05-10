package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.data.local.database.StockDatabase
import com.fxingsign.profitrackr.data.mapper.toStockPortfolio
import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.domain.SortOrder
import com.fxingsign.profitrackr.domain.repository.stocks.StockPortfolioRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StockPortfolioRepositoryImpl @Inject constructor(
    private val stockQuoteApi: StockQuoteApi,
    private val db: StockDatabase
) : StockPortfolioRepository {

    private val dao = db.daoStockPortfolio

    override suspend fun getStockPortfolio(searchTerm: String): Either<Failure, Flow<List<StockPortfolio>>> {
        //TODO: network availability check
        return when (true) {
            true -> {
                val stockStockPortfolioList = dao.getStockPortfolio(searchTerm)

                Either.Right(stockStockPortfolioList
                    .map { list ->
                        list.map {
                            it.toStockPortfolio()
                        }
                    })
            }
            false -> {
                return Either.Left(Failure.NetworkConnection)
            }
        }
    }
}