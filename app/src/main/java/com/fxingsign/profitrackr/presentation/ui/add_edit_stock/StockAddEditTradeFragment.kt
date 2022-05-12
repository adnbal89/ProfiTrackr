package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentAddEditStockTradeBinding
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormState
import com.fxingsign.profitrackr.util.failure
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.observe
import com.fxingsign.profitrackr.util.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class StockAddEditTradeFragment : Fragment(R.layout.fragment_add_edit_stock_trade) {

    private val viewModel: StockAddEditTradeViewModel by viewModels()
    val TAG = "StockAddEditTradeFragment"
    var stockId = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stocks = resources.getStringArray(R.array.stock_symbol_array)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, stocks
        )

        val binding = FragmentAddEditStockTradeBinding.bind(view)
        binding.apply {
            autocompleteTextViewSymbol.setAdapter(adapter)
            getStockQuoteList("AAPL")

            buttonBuy.setOnClickListener {

                val stockTradeFormState = StockTradeFormState(
                    symbol = binding.autocompleteTextViewSymbol.text.toString(),
                    buyPrice = binding.editTextPrice.text.toString(),
                    quantity = binding.editTextQuantity.text.toString(),
                    date = binding.datePickerDate.text.toString()
                )

                if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                    showInvalidStockIdSnackBar()
                } else {
                    validateTypedStockTrade(stockTradeFormState)
                }
                hideKeyboard(requireContext(), autocompleteTextViewSymbol)

            }

            buttonSell.setOnClickListener {
                val stockTradeFormState = StockTradeFormState(
                    symbol = binding.autocompleteTextViewSymbol.text.toString(),
                    buyPrice = binding.editTextPrice.text.toString(),
                    quantity = binding.editTextQuantity.text.toString(),
                    date = binding.datePickerDate.text.toString()
                )
                if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                    showInvalidStockIdSnackBar()
                } else {
                    validateTypedStockTrade(stockTradeFormState)
                }
                hideKeyboard(requireContext(), autocompleteTextViewSymbol)

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is StockAddEditTradeViewModel.Event.ShowErrorMessage ->
                        showSnackbar("Form Validation Failed")
                    is StockAddEditTradeViewModel.Event.NavigateUp ->
                        findNavController().navigateUp()
                }
            }
        }
    }


    private fun validateTypedStockTrade(stockTradeFormState: StockTradeFormState) {
        viewModel.validateTypedStockTrade(stockTradeFormState)
    }

    private fun getStockQuoteList(stockId: String){
        viewModel.getStockQuote(stockId)
    }

    private fun showInvalidStockIdSnackBar() {
        Snackbar.make(
            requireView(),
            "Stock not found in the list.",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}