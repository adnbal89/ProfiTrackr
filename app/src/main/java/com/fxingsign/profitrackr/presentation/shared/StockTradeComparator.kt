package com.fxingsign.profitrackr.presentation.shared

import androidx.recyclerview.widget.DiffUtil
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade


class StockTradeComparator : DiffUtil.ItemCallback<StockTrade>() {

    override fun areItemsTheSame(oldItem: StockTrade, newItem: StockTrade) =
        oldItem.symbol == newItem.symbol

    override fun areContentsTheSame(oldItem: StockTrade, newItem: StockTrade) =
        oldItem == newItem
}