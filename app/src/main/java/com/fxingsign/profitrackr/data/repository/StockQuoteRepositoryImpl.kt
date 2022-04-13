package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.data.remote.StockQuoteApi
import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDto
import com.fxingsign.profitrackr.domain.repository.stocks.StockQuoteRepository
import javax.inject.Inject

class StockQuoteRepositoryImpl @Inject constructor(
    private val api: StockQuoteApi
) : StockQuoteRepository {

    override suspend fun getStockQuoteById(stockId: String): StockQuoteDto {
        return api.getStockQuoteById(stockId)
    }

}