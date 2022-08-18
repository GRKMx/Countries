package com.gorkemersizer.countries.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Country(
    @SerializedName("name") val name: String? = null,
    @SerializedName("code") val code: String? = null
) : Serializable
