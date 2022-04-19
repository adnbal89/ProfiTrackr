package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockTotalDao {

    @Query(
        "SELECT SUM(quantity) as totalQty, " +
                "symbol," +
                " SUM(quantity*buyPrice) as avgCost, " +
                "ROUND(SUM(quantity*buyPrice)/SUM(quantity),2) avgPrice " +
                "FROM stock_trade_history WHERE symbol =:stockId GROUP BY symbol"
    )
    fun getStockPortfolioById(stockId: String): Flow<List<StockTotalEntity>>

    @Query(
        "SELECT SUM(quantity) as totalQty," +
                " symbol," +
                " SUM(quantity*buyPrice) as avgCost," +
                " ROUND(SUM(quantity*buyPrice)/SUM(quantity),2) avgPrice" +
                " FROM stock_trade_history GROUP BY symbol\n"
    )
    fun getStockPortfolio(): Flow<List<StockTotalEntity>>
}