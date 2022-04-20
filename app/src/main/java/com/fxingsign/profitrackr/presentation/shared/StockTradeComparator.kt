package com.fxingsign.profitrackr.presentation.shared

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade


class StockTradeComparator : DiffUtil.ItemCallback<StockTrade>() {

    val TAG = "buy"

    override fun areItemsTheSame(oldItem: StockTrade, newItem: StockTrade): Boolean {
        Log.d(TAG, oldItem.id.toString())
        Log.d(TAG, newItem.id.toString())
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StockTrade, newItem: StockTrade) =
        oldItem == newItem
}