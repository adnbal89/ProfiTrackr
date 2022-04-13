package com.fxingsign.profitrackr.data.repository

import com.fxingsign.profitrackr.domain.functional.Either
import com.fxingsign.profitrackr.domain.functional.exception.Failure
import com.fxingsign.profitrackr.domain.repository.stocks.StockTradeRepository
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockTrade
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase


//Buraya service koyulacak - repository DB ve Api ayrımı yapacak  StockTradeService
class StockTradeRepositoryImpl : StockTradeRepository {
    override suspend fun insertStockTradeToDatabase(stockTrade: StockTrade): Either<Failure, UseCase.None> {
        TODO("Not yet implemented")
    }
}