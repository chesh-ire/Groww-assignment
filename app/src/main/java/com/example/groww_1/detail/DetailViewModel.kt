package com.example.groww_1.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groww_1.model.CompanyOverviewResponse
import com.example.groww_1.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _stock = MutableStateFlow<CompanyOverviewResponse?>(null)
    val stock: StateFlow<CompanyOverviewResponse?> = _stock

    private val _priceHistory = MutableStateFlow<List<Float>>(emptyList())
    val priceHistory: StateFlow<List<Float>> = _priceHistory

    fun loadDetails(symbol: String) {
        viewModelScope.launch {
            val detailResponse = repository.getCompanyOverview(symbol)
            if (detailResponse.isSuccessful) {
                _stock.value = detailResponse.body()
            }

            val priceResponse = repository.getDailyTimeSeries(symbol)
            if (priceResponse.isSuccessful) {
                val series = priceResponse.body()?.timeSeries ?: emptyMap()
                val closing = series
                    .toSortedMap()
                    .values
                    .mapNotNull { it.close?.toFloatOrNull() }
                _priceHistory.value = closing.takeLast(10)
            }
        }
    }
    fun loadStockDetails(symbol: String) {
        viewModelScope.launch {
            val overviewResponse = repository.getCompanyOverview(symbol)
            if (overviewResponse.isSuccessful) {
                _stock.value = overviewResponse.body()
            }

            val timeSeriesResponse = repository.getDailyTimeSeries(symbol)
            if (timeSeriesResponse.isSuccessful) {
                val series = timeSeriesResponse.body()?.timeSeries?.values?.toList()
                val closing = series?.mapNotNull { it.close?.toFloatOrNull() } ?: emptyList()
                _priceHistory.value = closing.takeLast(10)
            }
        }
    }

}
