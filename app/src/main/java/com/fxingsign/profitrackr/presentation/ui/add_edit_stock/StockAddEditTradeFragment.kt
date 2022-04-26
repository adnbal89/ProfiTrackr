package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentAddEditStockTradeBinding
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.util.functional.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


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
            buttonBuy.setOnClickListener {

                if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                    showInvalidStockIdSnackBar()
                    hideKeyboard(requireContext(), autocompleteTextViewSymbol)
                } else {

                    val stockTradeDto = createStockTradeDtoFromUi(binding)
                    val copyStockTradeDto = stockTradeDto.copy(tradeType = "buy")
                    viewModel.insertStockTrade(copyStockTradeDto)
                    findNavController().navigateUp()
                }
            }

            buttonSell.setOnClickListener {
                if (!stocks.contains<String>(autocompleteTextViewSymbol.text.toString())) {
                    showInvalidStockIdSnackBar()
                    hideKeyboard(requireContext(), autocompleteTextViewSymbol)
                } else {

                    val stockTradeDto = createStockTradeDtoFromUi(binding)
                    val copyStockTradeDto = stockTradeDto.copy(tradeType = "sell")
                    viewModel.insertStockTrade(copyStockTradeDto)
                    findNavController().navigateUp()
                }
            }

        }
    }

    private fun createStockTradeDtoFromUi(binding: FragmentAddEditStockTradeBinding): StockTradeDto {
        return StockTradeDto(
            binding.autocompleteTextViewSymbol.text.toString(),
            binding.editTextQuantity.text.toString().toInt(),
            binding.editTextPrice.text.toString().toDouble(),
            binding.datePickerDate.text.toString(),
            "buy"
        )
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