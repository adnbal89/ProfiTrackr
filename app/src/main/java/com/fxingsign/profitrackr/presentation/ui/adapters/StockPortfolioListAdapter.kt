package com.fxingsign.profitrackr.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fxingsign.profitrackr.databinding.ItemStockBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.presentation.shared.StockPortfolioComparator
import com.fxingsign.profitrackr.presentation.shared.StockPortfolioViewHolder

class StockPortfolioListAdapter(
    private val onItemClick: (StockPortfolio) -> Unit
) : ListAdapter<StockPortfolio, StockPortfolioViewHolder>(StockPortfolioComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockPortfolioViewHolder {
        val binding = ItemStockBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return StockPortfolioViewHolder(binding,
            onItemClick = { position ->
                val stockPortfolio = getItem(position)
                if (stockPortfolio != null) {
                    onItemClick(stockPortfolio)
                }
            })
    }

    override fun onBindViewHolder(holder: StockPortfolioViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}