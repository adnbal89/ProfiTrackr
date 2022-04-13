package com.fxingsign.profitrackr.domain.repository.stocks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StockTrade(
    val symbol: String,
    val quantity: Int,
    val buyPrice: Double,
    val date: LocalDateTime,
    val tradeType: String
) : Parcelable
