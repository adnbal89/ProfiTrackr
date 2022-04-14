package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.data.local.database.StockTradeDatabase
import com.fxingsign.profitrackr.data.mapper.toStockTradeEntity
import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.domain.functional.Either
import com.fxingsign.profitrackr.domain.functional.exception.Failure
import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StockTradeRepositoryImpl @Inject constructor(
    private val stockQuoteApi: StockQuoteApi,
    private val db: StockTradeDatabase
) : StockTradeRepository {

    private val dao = db.dao

    override suspend fun insertStockTradeToDatabase(stockTrade: StockTrade): Either<Failure, None> {
        dao.insertStockTrade(stockTrade.toStockTradeEntity())
        return Either.Right(None())
    }
}