package com.fxingsign.profitrackr.data.local.entity


data class StockPortfolioEntity(
    val symbol: String,
    val totalQty: Int,
    val totalCost: Double,
    val avgPrice: Double
)