package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fxingsign.profitrackr.R
import com.fxingsign.profitrackr.databinding.FragmentAddEditStockTradeBinding
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockAddEditTradeFragment : Fragment(R.layout.fragment_add_edit_stock_trade) {

    private val viewModel: StockAddEditTradeViewModel by viewModels()
    val TAG = "StockAddEditTradeFragment"
    var stockId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditStockTradeBinding.bind(view)
        binding.apply {


            buttonBuy.setOnClickListener {
                stockId = editTextSymbol.text.toString()
                val stockTradeDto = createStockTradeDtoFromUi(binding)
                val copyStockTradeDto = stockTradeDto.copy(symbol = stockId, tradeType = "buy")
                viewModel.insertStockTrade(copyStockTradeDto)
                findNavController().navigateUp()
            }

            buttonSell.setOnClickListener {
                stockId = editTextSymbol.text.toString()
                val stockTradeDto = createStockTradeDtoFromUi(binding)
                val copyStockTradeDto = stockTradeDto.copy(symbol = stockId, tradeType = "sell")
                viewModel.insertStockTrade(copyStockTradeDto)
                findNavController().navigateUp()
            }

        }
    }

    private fun createStockTradeDtoFromUi(binding: FragmentAddEditStockTradeBinding): StockTradeDto {
        return StockTradeDto(
            "ASELS",
            binding.editTextQuantity.text.toString().toInt(),
            binding.editTextPrice.text.toString().toDouble(),
            binding.editTextPrice.text.toString(),
            "buy"
        )
    }


}