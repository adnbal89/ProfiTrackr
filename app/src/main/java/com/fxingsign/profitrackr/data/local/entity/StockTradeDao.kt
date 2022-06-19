package com.fxingsign.profitrackr.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM stock_trade_history")
    fun getStockTradeHistoryList(): Flow<List<StockTradeEntity>>

    @Query("SELECT * FROM stock_trade_history WHERE symbol =:stockId")
    fun getStockTradeHistoryListById(stockId: String): Flow<List<StockTradeEntity>>


}