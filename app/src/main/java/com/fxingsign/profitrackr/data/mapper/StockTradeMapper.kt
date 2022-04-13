package com.fxingsign.profitrackr.data.mapper

import com.fxingsign.profitrackr.data.local.entity.StockTradeEntity
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade

fun StockTradeEntity.toStockTrade(): StockTrade {
    return StockTrade(
        symbol = symbol,
        quantity = quantity,
        buyPrice = buyPrice,
        date = date,
        tradeType = tradeType
    )
}

fun StockTrade.toStockTradeEntity(): StockTradeEntity {

    return StockTradeEntity(
        symbol = symbol,
        quantity = quantity,
        buyPrice = buyPrice,
        date = date,
        tradeType = tradeType
    )
}