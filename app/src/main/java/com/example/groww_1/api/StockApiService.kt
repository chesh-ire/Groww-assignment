package com.example.groww_1.api

import com.example.groww_1.BuildConfig
import com.example.groww_1.model.CompanyOverviewResponse
import com.example.groww_1.model.TimeSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface StockApiService {

    @GET("query")
    suspend fun getCompanyOverview(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = BuildConfig.ALPHA_API_KEY
    ): Response<CompanyOverviewResponse>

    // Add other endpoints like top gainers/losers similarly
    @GET("query")
    suspend fun getDailyTimeSeries(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = BuildConfig.ALPHA_API_KEY
    ): Response<TimeSeriesResponse>

}
