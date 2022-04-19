package com.fxingsign.profitrackr.domain.repository.stocks.model

data class StockTotal(
    val symbol: String,
    val totalQty: Int,
    val avgCost: Double,
    val avgPrice: Double
)