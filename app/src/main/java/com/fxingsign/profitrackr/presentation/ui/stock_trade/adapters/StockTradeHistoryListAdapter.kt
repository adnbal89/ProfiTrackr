package com.fxingsign.profitrackr.presentation.ui.stock_trade.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fxingsign.profitrackr.databinding.ItemStockTradeHistoryBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.presentation.shared.StockTradeComparator
import com.fxingsign.profitrackr.presentation.shared.StockTradeViewHolder

class StockTradeHistoryListAdapter(
    private val onItemClick: (StockTrade) -> Unit
) : ListAdapter<StockTrade, StockTradeViewHolder>(StockTradeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockTradeViewHolder {
        val binding =
            ItemStockTradeHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockTradeViewHolder(binding,
            onItemClick = { position ->
                val stockTrade = getItem(position)
                if (stockTrade != null) {
                    onItemClick(stockTrade)
                }
            })
    }

    override fun onBindViewHolder(holder: StockTradeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}