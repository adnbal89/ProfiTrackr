package com.fxingsign.profitrackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.fxingsign.profitrackr.databinding.ActivityMainBinding
import com.fxingsign.profitrackr.presentation.ui.portfolio_listing.StockPortfolioListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var stockPortfolioListFragment: StockPortfolioListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment

        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}

private const val TAG_PORTFOLIO_LIST_FRAGMENT = "TAG_PORTFOLIO_LIST_FRAGMENT"
private const val TAG_STOCK_TRADE_HISTORY_FRAGMENT = "TAG_STOCK_TRADE_HISTORY_FRAGMENT"
private const val TAG_STOCK_ADD_EDIT_TRADE_FRAGMENT = "TAG_STOCK_ADD_EDIT_TRADE_FRAGMENT"


private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
