package com.fxingsign.profitrackr.domain.repository

import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDto

interface StockQuoteRepository {

    suspend fun getStockQuoteById(stockId: String): StockQuoteDto

}