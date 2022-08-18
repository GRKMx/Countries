package com.gorkemersizer.countries.data.entity

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("data") val data: List<Country>
)