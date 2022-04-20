package com.fxingsign.profitrackr.presentation.shared

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.ItemStockTradeHistoryBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade

class StockTradeViewHolder(
    private val binding: ItemStockTradeHistoryBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(stockTrade: StockTrade) {
        binding.apply {
            textViewTradeType.text = stockTrade.tradeType
            setViewBackgroundColor(textViewTradeType, stockTrade, binding)
            textViewQuantity.text = stockTrade.quantity.toString()
            textViewPrice.text = stockTrade.buyPrice.toString()
            textViewCost.text = (stockTrade.buyPrice * stockTrade.quantity).toString()
            textViewDate.text = stockTrade.date

        }
    }

    init {
        binding.apply {
            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }
    }
}

private fun setViewBackgroundColor(
    view: View,
    stockTrade: StockTrade,
    binding: ItemStockTradeHistoryBinding
) {
    when (stockTrade.tradeType) {
        "buy" -> {
            view
                .setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue_azure
                    )
                )
        }
        "sell" -> {
            view
                .setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
        }
    }
}