package com.fxingsign.profitrackr.data.remote

import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockQuoteApi {

    @GET("/api/v3/quote/{stockId}")
    suspend fun getStockQuoteById(
        @Path("stockId") stockId: String,
        @Query("apikey") apiKey: String = API_KEY
    ): StockQuoteDto


    companion object {
        const val API_KEY = "389d84ea6dfffb0fe7d772a23fafd375"
        const val BASE_URL = "https://financialmodelingprep.com"
    }
}