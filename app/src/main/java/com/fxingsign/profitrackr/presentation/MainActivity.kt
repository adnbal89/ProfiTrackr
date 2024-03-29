package com.fxingsign.profitrackr.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.ActivityMainBinding
import com.fxingsign.profitrackr.presentation.portfolio_listing.PortfolioListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var portfolioListFragment: PortfolioListFragment

    private var selectedIndex = 0
    private val selectedFragment get() = fragments[selectedIndex]

    private val fragments: Array<Fragment>
        get() = arrayOf(
            portfolioListFragment
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
            is PortfolioListFragment -> "Portfolyo Yönetimi"
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            portfolioListFragment = PortfolioListFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, portfolioListFragment, TAG_PORTFOLIO_LIST_FRAGMENT)
                .commit()
        } else {
            portfolioListFragment =
                supportFragmentManager.findFragmentByTag(TAG_PORTFOLIO_LIST_FRAGMENT) as PortfolioListFragment

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
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
