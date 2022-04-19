package com.fxingsign.profitrackr.data.mapper

import com.fxingsign.profitrackr.data.local.entity.StockTotalEntity
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTotal

fun StockTotalEntity.toStockTotal(): StockTotal {
    return StockTotal(
        symbol = symbol,
        totalQty = totalQty,
        avgCost = avgCost,
        avgPrice = avgPrice

    )
}

fun StockTotal.toStockTotalEntity(): StockTotalEntity {

    return StockTotalEntity(
        symbol = symbol,
        totalQty = totalQty,
        avgCost = avgCost,
        avgPrice = avgPrice
    )
}