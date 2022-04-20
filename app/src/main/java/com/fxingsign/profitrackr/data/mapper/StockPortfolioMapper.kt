package com.fxingsign.profitrackr.data.mapper

import com.fxingsign.profitrackr.data.local.entity.StockPortfolioEntity
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio

fun StockPortfolioEntity.toStockPortfolio(): StockPortfolio {
    return StockPortfolio(
        symbol = symbol,
        totalQty = totalQty,
        totalCost = totalCost,
        avgPrice = avgPrice
    )
}

fun StockPortfolio.toStockPortfolioEntity(): StockPortfolioEntity {

    return StockPortfolioEntity(
        symbol = symbol,
        totalQty = totalQty,
        totalCost = totalCost,
        avgPrice = avgPrice
    )
}