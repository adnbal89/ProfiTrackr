package com.fxingsign.profitrackr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fxingsign.profitrackr.data.local.entity.StockTradeDao
import com.fxingsign.profitrackr.data.local.entity.StockTradeEntity

@Database(
    entities = [StockTradeEntity::class],
    version = 1
)
abstract class StockTradeDatabase : RoomDatabase() {
    abstract val dao: StockTradeDao
}