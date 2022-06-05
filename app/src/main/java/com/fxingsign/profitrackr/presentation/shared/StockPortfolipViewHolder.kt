package com.fxingsign.profitrackr.presentation.shared

import androidx.recyclerview.widget.RecyclerView
import com.fxingsign.profitrackr.databinding.ItemStockBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio

class StockPortfolioViewHolder(
    private val binding: ItemStockBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(stockPortfolio: StockPortfolio) {
        binding.apply {
            textViewSymbol.text = stockPortfolio.symbol
            textViewCost.text = stockPortfolio.totalCost.roundTo(2).toString().toString()
            textViewQuantity.text = stockPortfolio.totalQty.toString()
            textViewPrice.text = stockPortfolio.avgPrice.roundTo(2).toString()
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


    fun Double.roundTo(n: Int): Double {
        return "%.${n}f".format(this).toDouble()
    }
}
