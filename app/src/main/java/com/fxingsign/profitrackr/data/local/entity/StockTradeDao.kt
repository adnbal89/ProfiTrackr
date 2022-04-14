package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockTradeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockTradeList(
        stockTradeEntity: List<StockTradeEntity>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockTrade(
        stockTradeEntity: StockTradeEntity
    )

    @Query("DELETE FROM stock_trade_history")
    suspend fun clearStockTradeHistoryTable()

}