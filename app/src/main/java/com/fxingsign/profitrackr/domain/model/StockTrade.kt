package com.fxingsign.profitrackr.domain.model

import java.time.LocalDateTime

data class StockTrade(
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: LocalDateTime,
    val tradeType: String
)
