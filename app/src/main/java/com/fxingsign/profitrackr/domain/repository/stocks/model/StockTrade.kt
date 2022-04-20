package com.fxingsign.profitrackr.domain.repository.stocks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockTrade(
    val id: Int,
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: String,
    val tradeType: String
) : Parcelable
