package com.fxingsign.profitrackr.presentation.ui.portfolio_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fxingsign.profitrackr.domain.repository.stocks.model.StockPortfolio
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.GetStockPortfolioUseCase
import com.fxingsign.profitrackr.domain.repository.stocks.use_case.UseCase.None
import com.fxingsign.profitrackr.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockPortfolioListViewModel @Inject constructor(
    private val getStockPortfolioUseCase: GetStockPortfolioUseCase
) : BaseViewModel() {
    val TAG = "StockPortfolioListViewModel"

    private val _stockPortfolioList: MutableLiveData<List<StockPortfolio>> = MutableLiveData()
    val stockPortfolioList: LiveData<List<StockPortfolio>> = _stockPortfolioList

    fun getStockPortfolioList() =
        getStockPortfolioUseCase(None(), viewModelScope) {
            it.fold(::handleFailure, ::handleStockPortfolioList)
        }

    private fun handleStockPortfolioList(stockPortfolioList: Flow<List<StockPortfolio>>) {

        viewModelScope.launch {
            stockPortfolioList.collect {
                _stockPortfolioList.value = it
            }
        }
    }
}