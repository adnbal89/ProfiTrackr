package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockQuoteUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.InsertStockTradeUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.ValidateStockTradeUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormState
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormStateResult
import com.fxingsign.profitrackr.util.Resource
import com.fxingsign.profitrackr.util.functional.exception.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAddEditTradeViewModel @Inject constructor(
    private val insertStockTradeUseCase: InsertStockTradeUseCase,
    private val validateStockTradeUseCase: ValidateStockTradeUseCase,
    private val getStockQuoteUseCase: GetStockQuoteUseCase
) : BaseViewModel() {

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    private val _formValidationResult: MutableLiveData<Boolean> = MutableLiveData()
    val formValidationResult: LiveData<Boolean> = _formValidationResult

    private val _stockQuoteList: MutableLiveData<List<StockQuote>> = MutableLiveData()
    val stockQuoteList: LiveData<List<StockQuote>> = _stockQuoteList

    val TAG = "StockAddEditTradeViewModel"

    /* var quantityData: String = ""

    val quantityValidator = DataValidator(quantityData).apply {
        //Whenever the condition of the predicate is true, the error message should be emitted
        addRule("username is required") { it.isDigitsOnly() }
    }*/


    private fun insertStockTrade(stockTradeDto: StockTradeDto) {
        insertStockTradeUseCase(params = stockTradeDto)
    }

    fun getStockQuote(stockId: String) {
        val stockList = listOf(stockId)

        getStockQuoteUseCase(params = GetStockQuoteUseCase.Params(stockList), viewModelScope) {
            it.fold(::handleFailure, ::handleStockQuoteSuccess)
        }
    }

    //This is called whenever the usernameLiveData and passwordLiveData changes
    /*fun <T,D> validateForm(binding: T, data: D):Boolean {

        val validators = listOf(quantityValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        val isValid = validatorResolver.isValid()
        Log.d(TAG,isValid.toString() )
        return isValid
    }*/

    fun validateTypedStockTrade(stockTradeFormState: StockTradeFormState) {
        validateStockTradeUseCase(params = stockTradeFormState, viewModelScope) {
            it.fold(::handleFailure, ::handleValidationSuccess)
        }
    }

    private fun handleStockQuoteSuccess(stockQuoteList: Flow<Resource<List<StockQuote>>>) {
        viewModelScope.launch {
            stockQuoteList.collect {
                _stockQuoteList.value = it.data
            }
        }

        Log.d(TAG, "stock result : $_stockQuoteList")

    }

    private fun handleValidationSuccess(stockTradeFormStateResult: StockTradeFormStateResult) {
        Log.d(
            "StockAddEditTradeFragment",
            "stockTradeFormStateResult.validationResult : ${stockTradeFormStateResult.validationResult}"
        )
        val stockTradeDto = createStockTradeDtoFromStateResult(stockTradeFormStateResult)
        insertStockTrade(stockTradeDto)
        viewModelScope.launch { eventChannel.send(Event.NavigateUp) }

    }

    override fun handleFailure(failure: Failure) {
        viewModelScope.launch { eventChannel.send(Event.ShowErrorMessage(failure)) }
    }


    private fun createStockTradeDtoFromStateResult(stockTradeFormStateResult: StockTradeFormStateResult): StockTradeDto {
        return StockTradeDto(
            stockTradeFormStateResult.symbol,
            stockTradeFormStateResult.quantity.toInt(),
            stockTradeFormStateResult.buyPrice.toDouble(),
            stockTradeFormStateResult.date,
            stockTradeFormStateResult.tradeType
        )
    }


    sealed class Event {
        data class ShowErrorMessage(val error: Throwable) : Event()
        object NavigateUp : Event()
    }

}