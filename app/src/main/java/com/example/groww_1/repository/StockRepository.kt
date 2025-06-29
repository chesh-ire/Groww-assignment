package com.example.groww_1.repository

import com.example.groww_1.api.StockApiService
import com.example.groww_1.model.CompanyOverviewResponse
import com.example.groww_1.model.StockGainItem
import retrofit2.Response
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val api: StockApiService
) {
    suspend fun getCompanyOverview(symbol: String): Response<CompanyOverviewResponse> {
        return api.getCompanyOverview(symbol = symbol)
    }

    suspend fun getGainerData(symbol: String): StockGainItem? {
        val response = api.getDailyTimeSeries(symbol = symbol)
        if (response.isSuccessful) {
            val series = response.body()?.timeSeries ?: return null
            val sortedDates = series.keys.sortedDescending()
            if (sortedDates.size >= 2) {
                val latest = series[sortedDates[0]]
                val previous = series[sortedDates[1]]
                val latestClose = latest?.close?.toDoubleOrNull()
                val previousClose = previous?.close?.toDoubleOrNull()
                if (latestClose != null && previousClose != null) {
                    val gain = ((latestClose - previousClose) / previousClose) * 100
                    return StockGainItem(symbol, gain)
                }
            }
        }
        return null
    }

    // Optional, if you want to expose just the raw response
    suspend fun getDailyTimeSeries(symbol: String) =
        api.getDailyTimeSeries(symbol = symbol)
}
