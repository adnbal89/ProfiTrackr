package com.fxingsign.profitrackr.domain.repository.stocks.use_case

import com.fxingsign.profitrackr.data.remote.dto.StockQuoteDtoItem
import com.fxingsign.profitrackr.domain.repository.stocks.StockQuoteRepository
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockQuoteUseCase.Params
import com.fxingsign.profitrackr.util.functional.Either
import com.fxingsign.profitrackr.util.functional.exception.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockQuoteUseCase @Inject constructor(
    private val repository: StockQuoteRepository
) : UseCase<Flow<List<StockQuoteDtoItem>>, Params>() {

    /*operator fun invoke(stockId: String): Flow<Resource<StockQuote>> = flow {
        try {
            emit(Resource.Loading<StockQuote>())
            val stock = repository.getStockQuoteById(stockId)
        } catch (e: HttpException) {

        } catch (e: IOException) {

        }
    }*/

    override suspend fun run(params: Params): Either<Failure, Flow<List<StockQuoteDtoItem>>> {
        return repository.getStockQuoteById(params.stockIdList.first())
    }

    data class Params(val stockIdList: List<String>)

}