package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "stock_trade_history")
data class StockTradeEntity(
    @PrimaryKey val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: String,
    val tradeType: String
)