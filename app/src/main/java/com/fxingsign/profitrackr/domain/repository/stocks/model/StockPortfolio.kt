package com.fxingsign.profitrackr.domain.repository.stocks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockPortfolio(
    val symbol: String,
    val totalQty: Int,
    val totalCost: Double,
    val avgPrice: Double
) : Parcelable