package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.domain.repository.stocks.StockQuoteRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockQuote
import com.fxingsign.profitrackr.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStockQuoteUseCase @Inject constructor(
    private val repository: StockQuoteRepository
) {
    operator fun invoke(stockId: String): Flow<Resource<StockQuote>> = flow {
        try {
            emit(Resource.Loading<StockQuote>())
            val stock = repository.getStockQuoteById(stockId)
        } catch (e: HttpException) {

        } catch (e: IOException) {

        }
    }
}