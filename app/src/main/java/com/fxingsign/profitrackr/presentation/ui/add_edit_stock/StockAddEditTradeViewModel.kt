package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.util.Log
import androidx.core.text.isDigitsOnly
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.InsertStockTradeUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import com.fxingsign.profitrackr.util.functional.validator.DataValidator
import com.fxingsign.profitrackr.util.functional.validator.LiveDataValidatorResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockAddEditTradeViewModel @Inject constructor(
    private val insertStockTradeUseCase: InsertStockTradeUseCase
) : BaseViewModel() {
    val TAG = "StockAddEditTradeViewModel"

    var quantityData: String = ""

    val quantityValidator = DataValidator(quantityData).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("username is required") { it.isDigitsOnly() }
    }

    fun insertStockTrade(stockTradeDto: StockTradeDto) {
        quantityData = stockTradeDto.buyPrice.toString()
        if(validateForm(binding = ))
        insertStockTradeUseCase(params = stockTradeDto)
        else
            Log.d(TAG, "inside else")
    }

    //This is called whenever the usernameLiveData and passwordLiveData changes
    fun <T,D> validateForm(binding: T, data: D):Boolean {

        val validators = listOf(quantityValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        val isValid = validatorResolver.isValid()
        Log.d(TAG,isValid.toString() )
        return isValid
    }

}