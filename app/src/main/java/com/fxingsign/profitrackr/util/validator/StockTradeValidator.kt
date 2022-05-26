package com.fxingsign.profitrackr.util.validator

import android.util.Log
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormStateResult
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import javax.inject.Inject

class StockTradeValidator @Inject constructor() {


    fun execute(
        symbol: String,
        quantity: String,
        price: String,
        date: String,
        tradeType: String
    ): Either<Failure, StockTradeFormStateResult> {

        val stockTradeFormStateResult =
            StockTradeFormStateResult(true)

        Log.d("StockAddEditTradeFragment", "inside validator")
        if (symbol.isNullOrBlank()) {
            Log.d("StockAddEditTradeFragment", "symbol.isNullOrBlank()")

            stockTradeFormStateResult.validationResult = false
            stockTradeFormStateResult.symbolError = "symbol cannot be empty"
        }
        if (quantity.isNullOrBlank()) {
            Log.d("StockAddEditTradeFragment", "quantity.isNullOrBlank()")

            stockTradeFormStateResult.validationResult = false
            stockTradeFormStateResult.quantityError = "quantity cannot be empty"
        }
        if (price.isNullOrBlank() || price.toDoubleOrNull() == null) {
            Log.d("StockAddEditTradeFragment", "price.isNullOrBlank()")

            stockTradeFormStateResult.validationResult = false
            stockTradeFormStateResult.buyPriceError = "please specify correct price"
        }
        if (date.isNullOrBlank()) {
            Log.d("StockAddEditTradeFragment", "date.isNullOrBlank()")

            stockTradeFormStateResult.validationResult = false
            stockTradeFormStateResult.dateError = "date cannot be empty"
        }
        Log.d("StockAddEditTradeFragment", stockTradeFormStateResult.validationResult.toString())

        return when (stockTradeFormStateResult.validationResult) {
            true -> {
                val copyStockTradeFormStateResult = stockTradeFormStateResult.copy(
                    symbol = symbol,
                    quantity = quantity,
                    buyPrice = price,
                    date = date,
                    tradeType = tradeType
                )
                Either.Right(copyStockTradeFormStateResult)
            }
            false -> Either.Left(Failure.ValidationError)
        }
    }
}