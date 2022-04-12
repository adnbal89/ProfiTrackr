package com.fxingsign.profitrackr.presentation.portfolio_listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentPortfolioListBinding

class PortfolioListFragment : Fragment(R.layout.fragment_portfolio_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPortfolioListBinding.bind(view)
        binding.apply {

        }
    }
}