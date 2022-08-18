package com.gorkemersizer.countries.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountryDetail(
    @SerializedName("name") val name: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("flagImageUri") val flagImageUri: String?= null,
    @SerializedName("wikiDataId") val wikiDataId: String? = null,
) : Serializable
