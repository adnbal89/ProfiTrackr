package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockPortfolioDao {

    @Query(
        "SELECT SUM(quantity) as totalQty, " +
                "symbol," +
                "SUM(quantity*buyPrice) as totalCost, " +
                "ROUND(SUM(quantity*buyPrice)/SUM(quantity),2) avgPrice " +
                "FROM stock_trade_history WHERE symbol =:stockId AND tradeType = 'buy' GROUP BY symbol ORDER BY symbol"
    )
    fun getStockPortfolioById(stockId: String): Flow<List<StockPortfolioEntity>>

    @Query(
        "SELECT SUM(b.quantity) - COALESCE((SELECT SUM(a.quantity) as totalQty FROM stock_trade_history a WHERE symbol LIKE '%' || :searchTerm || '%' and a.symbol = b.symbol and tradeType = 'sell' GROUP BY symbol ORDER BY symbol),0) as totalQty, " +
                "b.symbol," +
                "SUM(b.quantity*b.buyPrice) as totalCost," +
                "ROUND(SUM(b.quantity*b.buyPrice)/SUM(b.quantity),2) avgPrice" +
                " FROM stock_trade_history b WHERE b.symbol " +
                "LIKE '%' || :searchTerm || '%' and b.tradeType = 'buy' GROUP BY b.symbol ORDER BY b.symbol"
    )
    fun getStockPortfolio(searchTerm: String): Flow<List<StockPortfolioEntity>>
}

//- (SELECT SUM(quantity) FROM stock_trade_history a WHERE symbol LIKE '%' || :searchTerm || '%' AND a.symbol = b.symbol AND tradeType = 'sell' GROUP BY symbol)) as totalQty," +
//         - (SELECT SUM(quantity) FROM stock_trade_history a WHERE a.symbol = b.symbol AND tradeType = 'sell' GROUP BY symbol) as totalQty, " +
//