package com.fxingsign.profitrackr.data.local.entity


data class StockTotalEntity(
    val symbol: String,
    val totalQty: Int,
    val avgCost: Double,
    val avgPrice: Double
)