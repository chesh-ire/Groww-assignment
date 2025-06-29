package com.example.groww_1.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeSeriesResponse(
    @Json(name = "Time Series (Daily)")
    val timeSeries: Map<String, DailyStock>?
)

@JsonClass(generateAdapter = true)
data class DailyStock(
    @Json(name = "1. open") val open: String?,
    @Json(name = "4. close") val close: String?
)
