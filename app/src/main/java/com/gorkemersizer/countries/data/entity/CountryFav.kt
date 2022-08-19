package com.gorkemersizer.countries.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "country_favs")
data class CountryFav(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("code") @NotNull val code: String,
    @SerializedName("name") @NotNull val name: String,
) : Serializable

