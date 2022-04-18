package com.fxingsign.profitrackr.presentation.ui.portfolio_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentStockListBinding

class PortfolioListFragment : Fragment(R.layout.fragment_stock_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentStockListBinding.bind(view)
        binding.apply {

        }
    }
}