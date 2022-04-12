package com.fxingsign.profitrackr.presentation.stock_trade

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockTradeBinding

class StockTradeFragment : Fragment(R.layout.fragment_stock_trade) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStockTradeBinding.bind(view)
        binding.apply {

        }
    }
}