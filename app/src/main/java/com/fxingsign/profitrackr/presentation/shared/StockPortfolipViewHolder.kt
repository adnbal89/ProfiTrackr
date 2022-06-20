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
            val profit =
                ((stockPortfolio.totalQty * stockPortfolio.lastPrice) - (stockPortfolio.totalQty * stockPortfolio.avgPrice)).roundTo(
                    2
                )
            val cost = (stockPortfolio.totalQty * stockPortfolio.avgPrice).roundTo(2)

            textViewSymbol.text = stockPortfolio.symbol
            textViewCost.text = cost.toString()
            textViewQuantity.text = stockPortfolio.totalQty.toString()
            textViewPrice.text = stockPortfolio.avgPrice.roundTo(2).toString()
            textViewTotal.text =
                (stockPortfolio.totalQty * stockPortfolio.lastPrice).roundTo(2).toString()
            textViewLastPrice.text = stockPortfolio.lastPrice.roundTo(2).toString()
            textViewDifference.text =
                (stockPortfolio.lastPrice - stockPortfolio.avgPrice).roundTo(2).toString()
            textViewProfit.text = profit
                .toString()

            textViewProfitPercentage.text = (profit / cost * 100).roundTo(1).toString() + " %"
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
