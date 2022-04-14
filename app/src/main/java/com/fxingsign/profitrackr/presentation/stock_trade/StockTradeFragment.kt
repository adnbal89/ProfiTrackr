package com.fxingsign.profitrackr.presentation.stock_trade

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockTradeBinding
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockTradeFragment : Fragment(R.layout.fragment_stock_trade) {

    private val viewModel: StockTradeViewModel by viewModels()
    val TAG = "Buy"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStockTradeBinding.bind(view)
        binding.apply {

        }
        binding.buttonBuy.setOnClickListener {
            Log.d(TAG, "Buy CLicked")
            viewModel.insertStockTrade(
                StockTrade(
                    "ASELS",
                    1500,
                    23.5,
                    "14.04.2022",
                    "buy"
                )
            )
        }
    }
}