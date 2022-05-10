package com.fxingsign.profitrackr.presentation.form_states

data class StockTradeFormStateResult(
    var validationResult: Boolean,
    var symbolError: String? = null,
    var quantityError: String? = null,
    var buyPriceError: String? = null,
    var dateError: String? = null,
    val symbol: String = "",
    val quantity: String = "",
    val buyPrice: String = "",
    val date: String = "",
    val tradeType: String = ""
)