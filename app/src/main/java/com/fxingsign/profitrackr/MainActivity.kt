package com.fxingsign.profitrackr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.databinding.ActivityMainBinding
import com.fxingsign.profitrackr.presentation.stock_trade.StockTradeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var stockTradeFragment: StockTradeFragment

    private var selectedIndex = 0
    private val selectedFragment get() = fragments[selectedIndex]

    private val fragments: Array<Fragment>
        get() = arrayOf(
            stockTradeFragment
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
            is StockTradeFragment -> "Hisse Al/Sat"
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            stockTradeFragment = StockTradeFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, stockTradeFragment, TAG_PORTFOLIO_LIST_FRAGMENT)
                .commit()
        } else {
            stockTradeFragment =
                supportFragmentManager.findFragmentByTag(TAG_STOCK_TRADE_FRAGMENT) as StockTradeFragment

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
private const val TAG_STOCK_TRADE_FRAGMENT = "TAG_STOCK_TRADE_FRAGMENT"

private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
