package com.fxingsign.profitrackr.presentation.ui.add_edit_stock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDtoItem
import com.fxingsign.profitrackr.domain.repository.stocks.dto.StockTradeDto
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockQuoteUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.InsertStockTradeUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.ValidateStockTradeUseCase
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormState
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormStateResult
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
    private val getStockQuoteUseCase: GetStockQuoteUseCase,
    private val state: SavedStateHandle
) : BaseViewModel() {
    val TAG = "StockAddEditTradeViewModel"

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    private val _formValidationResult: MutableLiveData<Boolean> = MutableLiveData()
    val formValidationResult: LiveData<Boolean> = _formValidationResult

    private val _stockQuoteList: MutableLiveData<List<StockQuoteDtoItem>> = MutableLiveData()
    val stockQuoteList: LiveData<List<StockQuoteDtoItem>> = _stockQuoteList


    //get the object from nav_graph ,
    // argument has to be the same as the nav_Graph -> argument name
    val stockTrade = state.get<StockTrade>("stockTrade")

    //add or edit operation. if add then page will load as a blank add screen else page will load as edit screen
    val operationType = state.get<String>("operation_type")

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
        Log.d(TAG, "getStockQuote : $stockList")


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

    private fun handleStockQuoteSuccess(stockQuoteList: Flow<List<StockQuoteDtoItem>>) {
        viewModelScope.launch {
            stockQuoteList.collect {
                Log.d(TAG, "handleStockQuoteSuccess : ${it.first().price}")
                _stockQuoteList.value = it
            }
        }
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
            0,
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