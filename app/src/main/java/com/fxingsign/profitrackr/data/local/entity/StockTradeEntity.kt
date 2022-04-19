package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stock_trade_history")
data class StockTradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: String,
    val tradeType: String
)
