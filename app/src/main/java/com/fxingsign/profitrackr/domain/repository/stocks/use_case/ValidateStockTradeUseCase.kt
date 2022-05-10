package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import android.util.Log
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormState
import com.fxingsign.profitrackr.presentation.form_states.StockTradeFormStateResult
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import com.fxingsign.profitrackr.util.validator.StockTradeValidator
import javax.inject.Inject

class ValidateStockTradeUseCase @Inject constructor(
    private val stockTradeValidator: StockTradeValidator
) : UseCase<StockTradeFormStateResult, StockTradeFormState>() {

    override suspend fun run(params: StockTradeFormState): Either<Failure, StockTradeFormStateResult> {
        Log.d("StockAddEditTradeFragment", "inside usecase")
        val result = stockTradeValidator.execute(
            params.symbol,
            params.quantity,
            params.buyPrice,
            params.date
        )
        return result
    }
}