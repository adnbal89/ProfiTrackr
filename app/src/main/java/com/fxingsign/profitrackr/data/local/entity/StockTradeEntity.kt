package com.fxingsign.profitrackr.data.local.entity

import java.time.LocalDateTime

data class StockTradeEntity(
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: LocalDateTime,
    val tradeType: String
)