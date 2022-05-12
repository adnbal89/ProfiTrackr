package com.fxingsign.profitrackr.domain.repository.stocks

import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDtoItem
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.util.Resource
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow

interface StockQuoteRepository {

    suspend fun getStockQuoteById(stockId: String): Either<Failure, Flow<List<StockQuoteDtoItem>>>

    suspend fun getStockQuoteList(stockIdList: List<String>): Either<Failure, Flow<Resource<List<StockQuote>>>>

}