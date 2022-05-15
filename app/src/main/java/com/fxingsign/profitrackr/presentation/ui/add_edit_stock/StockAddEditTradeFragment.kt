package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDtoItem
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

    private lateinit var binding: FragmentAddEditStockTradeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {
            observe(stockQuoteList, ::renderStockQuoteList)
            failure(failure, ::handleFailure)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stocks = resources.getStringArray(R.array.stock_symbol_array)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, stocks
        )



        binding = FragmentAddEditStockTradeBinding.bind(view)
        binding.apply {
            autocompleteTextViewSymbol.setAdapter(adapter)

            autocompleteTextViewSymbol.onItemClickListener = OnItemClickListener { _, _, pos, _ ->
                getStockQuoteList("AAPL")
            }


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


            binding.editTextPrice.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Check Null and calculate cost
                    // calculate when price and quantity are not null
                    if (!binding.editTextPrice.text.isNullOrBlank() && !binding.editTextQuantity.text.isNullOrBlank()) {
                        binding.editTextCost.text = (binding.editTextQuantity.text
                            .toString().toInt() * binding.editTextPrice.text.toString()
                            .toDouble()).toString()
                    }
                }
            })

            //TODO : refactor here -> to viewmodel
            binding.editTextQuantity.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Check Null and calculate cost
                    // calculate when price and quantity are not null

                    if (!binding.editTextPrice.text.isNullOrBlank() && !binding.editTextQuantity.text.isNullOrBlank()) {
                        binding.editTextCost.text = (binding.editTextQuantity.text
                            .toString().toInt() * binding.editTextPrice.text.toString()
                            .toDouble()).toString()
                    }
                }
            })
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

    private fun getStockQuoteList(stockId: String) {
        viewModel.getStockQuote(stockId)
    }

    private fun renderStockQuoteList(stockQuoteList: List<StockQuoteDtoItem>?) {
        binding.editTextPrice.setText(stockQuoteList?.first()?.price.toString())
    }

    private fun handleFailure(failure: Failure?) {

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