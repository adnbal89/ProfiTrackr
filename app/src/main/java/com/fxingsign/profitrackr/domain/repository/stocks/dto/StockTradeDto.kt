package com.fxingsign.profitrackr.domain.repository.stocks.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockTradeDto(
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: String,
    val tradeType: String
) : Parcelable
