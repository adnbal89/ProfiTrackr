package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDtoItem
import com.fxingsign.profitrackr.domain.repository.stocks.StockQuoteRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.util.Resource
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockQuoteRepositoryImpl @Inject constructor(
    private val api: StockQuoteApi
) : StockQuoteRepository {
    val TAG = "StockAddEditTradeViewModel"

    override suspend fun getStockQuoteById(stockId: String): Either<Failure, Flow<List<StockQuoteDtoItem>>> {
        return try {
            val res = api.getStockQuoteById(stockId)
            val apiResult =
                flowOf(res)
            Either.Right(apiResult)

        } catch (exception: HttpException) {
            Either.Left(Failure.NetworkConnection)
        } catch (exception: IOException) {
            Either.Left(Failure.IOException)
        }
    }


    override suspend fun getStockQuoteList(stockIdList: List<String>): Either<Failure, Flow<Resource<List<StockQuote>>>> {
        val stockIdListString = stockIdList.joinToString(separator = ",") { it -> it }.uppercase()
        //TODO : fixme
        return Either.Left(Failure.ValidationError)
    }

}