package com.example.groww_1.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyOverviewResponse(
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "AssetType") val assetType: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Exchange") val exchange: String?,
    @field:Json(name = "Currency") val currency: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Sector") val sector: String?,
    @field:Json(name = "Industry") val industry: String?
    
)
