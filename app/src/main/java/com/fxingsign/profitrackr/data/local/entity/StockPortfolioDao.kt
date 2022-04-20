package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockPortfolioDao {

    @Query(
        "SELECT SUM(quantity) as totalQty, " +
                "symbol," +
                " SUM(quantity*buyPrice) as totalCost, " +
                "ROUND(SUM(quantity*buyPrice)/SUM(quantity),2) avgPrice " +
                "FROM stock_trade_history WHERE symbol =:stockId GROUP BY symbol"
    )
    fun getStockPortfolioById(stockId: String): Flow<List<StockPortfolioEntity>>

    @Query(
        "SELECT SUM(quantity) as totalQty," +
                " symbol," +
                " SUM(quantity*buyPrice) as avgCost," +
                " ROUND(SUM(quantity*buyPrice)/SUM(quantity),2) avgPrice" +
                " FROM stock_trade_history GROUP BY symbol"
    )
    fun getStockPortfolio(): Flow<List<StockPortfolioEntity>>
}