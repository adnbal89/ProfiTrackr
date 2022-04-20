package com.fxingsign.profitrackr.domain.repository.stocks.model

data class StockPortfolio(
    val symbol: String,
    val totalQty: Int,
    val totalCost: Double,
    val avgPrice: Double
)