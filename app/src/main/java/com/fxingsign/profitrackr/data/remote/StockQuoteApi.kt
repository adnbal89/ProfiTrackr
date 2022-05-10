package com.fxingsign.profitrackr.data.remote

import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDto
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.util.Resource
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockQuoteApi {

    @GET("/api/v3/quote/{stockId}")
    suspend fun getStockQuoteById(
        @Path("stockId") stockId: String,
        @Query("apikey") apiKey: String = API_KEY
    ): Either<Failure, Flow<Resource<StockQuote>>>

    @GET("/api/v3/quote/{stockIdListString}")
    suspend fun getStockQuoteList(
        @Path("stockIdListString") stockIdListString: String,
        @Query("apikey") apiKey: String = API_KEY
    ): Either<Failure, Flow<Resource<List<StockQuote>>>>




    companion object {
        const val API_KEY = "389d84ea6dfffb0fe7d772a23fafd375"
        const val BASE_URL = "https://financialmodelingprep.com"
    }
}