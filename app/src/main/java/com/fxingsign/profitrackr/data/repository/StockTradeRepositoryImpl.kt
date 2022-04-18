package com.fxingsign.profitrackr.data.repository

import android.util.Log
import com.fxingsign.profitrackr.data.local.database.StockTradeDatabase
import com.fxingsign.profitrackr.data.mapper.toStockTrade
import com.fxingsign.profitrackr.data.mapper.toStockTradeEntity
import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getStockTradeHistory(): Either<Failure, Flow<List<StockTrade>>> {
        //TODO: network availability check !
        return when (true) {
            true -> {
                val stockHistList = dao.getStockTradeHistoryList()

                Either.Right(stockHistList
                    .map { list ->
                        Log.d("buy", list.size.toString())
                        list.map {
                            Log.d("buy", it.symbol)
                            it.toStockTrade()
                        }
                    })
            }
            false -> {
                return Either.Left(Failure.NetworkConnection)
            }
        }
    }
}