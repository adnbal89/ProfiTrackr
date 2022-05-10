package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDto
import com.fxingsign.profitrackr.domain.repository.stocks.StockQuoteRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.util.Resource
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockQuoteRepositoryImpl @Inject constructor(
    private val api: StockQuoteApi
) : StockQuoteRepository {

    override suspend fun getStockQuoteById(stockId: String): StockQuoteDto {
        TODO("Not yet implemented")
    }


    override suspend fun getStockQuoteList(stockIdList: List<String>): Either<Failure, Flow<Resource<List<StockQuote>>>> {
        val stockIdListString = stockIdList.joinToString (separator = ",") { it -> it }.uppercase()

        return api.getStockQuoteList(stockIdListString)
    }

}