package com.fxingsign.profitrackr.presentation.form_states

data class StockTradeFormState(
    val symbol: String = "",
    val symbolError: String? = null,
    val quantity: String = "",
    val quantityError: String? = null,
    val buyPrice: String = "",
    val buyPriceError: String? = null,
    val date: String = "",
    val dateError: String? = "",
    val tradeType: String = ""
)