package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
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
import java.util.*


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


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stocks = resources.getStringArray(R.array.nasdaq_stock_symbol_array)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, stocks
        )

        binding = FragmentAddEditStockTradeBinding.bind(view)


        binding.apply {
            if (viewModel.operationType == "edit") {

                autocompleteTextViewSymbol.setText(viewModel.stockTrade?.symbol)
                editTextQuantity.setText(viewModel.stockTrade?.quantity.toString())
                editTextPrice.setText(viewModel.stockTrade?.buyPrice?.roundTo(2).toString())
                editTextCost.text =
                    (viewModel.stockTrade?.quantity?.times(viewModel.stockTrade?.buyPrice!!)
                        ?.roundTo(2)).toString()
                datePickerDate.setText(viewModel.stockTrade?.date)
                //check the relevant tradetype according to stockTrade's saved tradetype.
                when (viewModel.stockTrade?.tradeType) {
                    "buy" -> toggleGroup.check(toggleBuy.id)
                    "sell" -> toggleGroup.check(toggleSell.id)
                }
                textViewTradeType.visibility = View.VISIBLE
                toggleGroup.visibility = View.VISIBLE

                buttonBuy.setText(R.string.save)
                buttonSell.setText(R.string.cancel)
                buttonSell.setBackgroundColor(resources.getColor(R.color.gray))
            }
            autocompleteTextViewSymbol.setAdapter(adapter)

            autocompleteTextViewSymbol.onItemClickListener =
                OnItemClickListener { parent, _, pos, _ ->
                    getStockQuoteList(parent.getItemAtPosition(pos).toString())
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

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
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

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    //Check Null and calculate cost
                    // calculate when price and quantity are not null

                    if (!binding.editTextPrice.text.isNullOrBlank() && !binding.editTextQuantity.text.isNullOrBlank()) {
                        binding.editTextCost.text = (binding.editTextQuantity.text
                            .toString().toInt() * binding.editTextPrice.text.toString()
                            .toDouble()).roundTo(2).toString()
                    }
                }
            })


            //TODO : Refactor -> Move inner functions to viewmodel
            datePickerDate.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)
                        val datePickerDialog = DatePickerDialog(
                            view.context,
                            { _, myear, mmonth, mdayOfMonth ->
                                datePickerDate.setText("$mdayOfMonth/$mmonth/$myear")
                            },
                            year,
                            month,
                            day
                        )
                        datePickerDialog.show()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }

            buttonBuy.setOnClickListener {
                var tradeType: String = "buy"
                if (viewModel.operationType == "edit") {
                    //it means cancel button pressed, do nothing and go back
                    tradeType = when (toggleGroup.checkedButtonId) {
                        toggleBuy.id -> "buy"
                        toggleSell.id -> "sell"
                        else -> ""
                    }

                }


                val stockTradeFormState = StockTradeFormState(
                    id = viewModel.stockTrade?.id ?: 0,
                    symbol = binding.autocompleteTextViewSymbol.text.toString(),
                    buyPrice = binding.editTextPrice.text.toString(),
                    quantity = binding.editTextQuantity.text.toString(),
                    date = binding.datePickerDate.text.toString(),
                    tradeType = tradeType
                )

                if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                    showInvalidStockIdSnackBar()
                } else {
                    validateTypedStockTrade(stockTradeFormState)
                }
                hideKeyboard(requireContext(), autocompleteTextViewSymbol)

            }

            buttonSell.setOnClickListener {
                if (viewModel.operationType == "edit") {
                    //it means cancel button pressed, do nothing and go back
                    findNavController().navigateUp()
                } else {
                    val stockTradeFormState = StockTradeFormState(
                        id = viewModel.stockTrade?.id ?: 0,
                        symbol = binding.autocompleteTextViewSymbol.text.toString(),
                        buyPrice = binding.editTextPrice.text.toString(),
                        quantity = binding.editTextQuantity.text.toString(),
                        date = binding.datePickerDate.text.toString(),
                        tradeType = "sell"
                    )
                    if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                        showInvalidStockIdSnackBar()
                    } else {
                        validateTypedStockTrade(stockTradeFormState)
                    }
                    hideKeyboard(requireContext(), autocompleteTextViewSymbol)
                }
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

    fun Double.roundTo(n: Int): Double {
        return "%.${n}f".format(this).toDouble()
    }

}