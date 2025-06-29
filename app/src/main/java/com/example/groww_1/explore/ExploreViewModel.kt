package com.example.groww_1.explore


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groww_1.model.CompanyOverviewResponse
import com.example.groww_1.model.StockGainItem
import com.example.groww_1.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _stock = MutableStateFlow<CompanyOverviewResponse?>(null)
    val stock: StateFlow<CompanyOverviewResponse?> = _stock

    private val _allStockChanges = MutableStateFlow<List<StockGainItem>>(emptyList())
    val allStockChanges: StateFlow<List<StockGainItem>> = _allStockChanges

    private val _topGainers = MutableStateFlow<List<StockGainItem>>(emptyList())
    val topGainers: StateFlow<List<StockGainItem>> = _topGainers

    private val _topLosers = MutableStateFlow<List<StockGainItem>>(emptyList())
    val topLosers: StateFlow<List<StockGainItem>> = _topLosers


    fun loadAllStocks() {
        viewModelScope.launch {
            val symbols = listOf("AAPL", "MSFT", "GOOGL", "META", "AMZN", "TSLA", "NFLX", "NVDA", "ORCL", "INTC")
            val result = symbols.mapNotNull { repository.getGainerData(it) }

            _allStockChanges.value = result
            _topGainers.value = result.filter { it.gainPercent > 0 }.sortedByDescending { it.gainPercent }.take(3)
            _topLosers.value = result.filter { it.gainPercent < 0 }.sortedBy { it.gainPercent }.take(3)
        }
    }
}
