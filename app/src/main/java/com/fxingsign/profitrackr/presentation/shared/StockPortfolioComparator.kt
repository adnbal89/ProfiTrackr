package com.fxingsign.profitrackr.presentation.shared

import androidx.recyclerview.widget.DiffUtil
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio


class StockPortfolioComparator : DiffUtil.ItemCallback<StockPortfolio>() {

    val TAG = "buy"

    override fun areItemsTheSame(oldItem: StockPortfolio, newItem: StockPortfolio) =
        oldItem.symbol == newItem.symbol


    override fun areContentsTheSame(oldItem: StockPortfolio, newItem: StockPortfolio) =
        oldItem == newItem
}