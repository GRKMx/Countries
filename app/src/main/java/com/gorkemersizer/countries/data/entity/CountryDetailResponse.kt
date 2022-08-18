package com.gorkemersizer.countries.data.entity

import com.google.gson.annotations.SerializedName

data class CountryDetailResponse(
    @SerializedName("data") val data: CountryDetail
)
