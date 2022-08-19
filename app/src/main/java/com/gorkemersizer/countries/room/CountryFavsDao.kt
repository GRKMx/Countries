package com.gorkemersizer.countries.room

import androidx.room.*
import com.gorkemersizer.countries.data.entity.CountryFav

@Dao
interface CountryFavsDao {
    @Query("SELECT * FROM country_favs")
    suspend fun getAllFavCountries() : List<CountryFav>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountryFav(countryFav: CountryFav)

    @Delete
    suspend fun deleteCountryFav(countryFav: CountryFav)
}