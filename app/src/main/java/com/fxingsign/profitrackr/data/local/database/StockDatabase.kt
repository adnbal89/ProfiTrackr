package com.fxingsign.profitrackr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fxingsign.profitrackr.data.local.entity.StockPortfolioDao
import com.fxingsign.profitrackr.data.local.entity.StockTradeDao
import com.fxingsign.profitrackr.data.local.entity.StockTradeEntity

@Database(
    entities = [StockTradeEntity::class],
    version = 2
)
abstract class StockDatabase : RoomDatabase() {

    abstract val daoStockTrade: StockTradeDao
    abstract val daoStockPortfolio: StockPortfolioDao

}