package com.fxingsign.profitrackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.databinding.ActivityMainBinding
import com.fxingsign.profitrackr.presentation.ui.portfolio_listing.StockPortfolioListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var stockPortfolioListFragment: StockPortfolioListFragment

    private var selectedIndex = 0
    private val selectedFragment get() = fragments[selectedIndex]

    private val fragments: Array<Fragment>
        get() = arrayOf(
            stockPortfolioListFragment
        )

    private fun selectFragment(selectedFragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }

        transaction.commit()
        title = when (selectedFragment) {
            is StockPortfolioListFragment -> "Hisse Geçmişi"
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            stockPortfolioListFragment = StockPortfolioListFragment()

            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    stockPortfolioListFragment,
                    TAG_PORTFOLIO_LIST_FRAGMENT
                )
                .commit()
        } else {
            stockPortfolioListFragment =
                supportFragmentManager.findFragmentByTag(TAG_PORTFOLIO_LIST_FRAGMENT) as StockPortfolioListFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }

        selectFragment(selectedFragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)

    }
}

private const val TAG_PORTFOLIO_LIST_FRAGMENT = "TAG_PORTFOLIO_LIST_FRAGMENT"
private const val TAG_STOCK_TRADE_HISTORY_FRAGMENT = "TAG_STOCK_TRADE_HISTORY_FRAGMENT"

private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
