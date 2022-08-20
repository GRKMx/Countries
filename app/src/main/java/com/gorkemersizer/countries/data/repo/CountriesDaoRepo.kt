package com.gorkemersizer.countries.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gorkemersizer.countries.data.entity.*
import com.gorkemersizer.countries.retrofit.CountriesDao
import com.gorkemersizer.countries.room.CountryFavsDao
import com.gorkemersizer.countries.util.Constants.API_KEY
import com.gorkemersizer.countries.util.Constants.LIMIT
import kotlinx.coroutines.*

class CountriesDaoRepo(var cdao: CountriesDao, var cfdao: CountryFavsDao) {
    var favList: MutableLiveData<List<CountryFav>> = MutableLiveData()

    /**
     *  Countries ----------------------------------------------------------------------------------
     */

    /**
     *  Get a list of country by api
     */

    suspend fun getAllCountries(): CountryResponse {
        Log.d("daorepo","daorepo all country çalıştı")
        return cdao.getCountries(API_KEY, LIMIT)
    }

    /**
     *  Get a country with details by api
     */

    suspend fun getCountry(countryCode: String): CountryDetailResponse {
        Log.d("daorepo","daorepo detail country çalıştı")
        return cdao.getCountryDetail(countryCode, API_KEY)
    }

    /**
     *   Saved Countries ---------------------------------------------------------------------------
     */

    /**
     *  Return saved countries
     */

    fun getCountryFavList() : MutableLiveData<List<CountryFav>> {
        return favList
    }

    /**
     *  Save a country to database
     */

    fun addCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newCountryFav = CountryFav(code, name)
            cfdao.addCountryFav(newCountryFav)
            getAllCountryFavs()
        }
    }

    /**
     *  Delete a saved country from database
     */

    fun deleteCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val deletedCountryFav = CountryFav(code, name)
            cfdao.deleteCountryFav(deletedCountryFav)
            getAllCountryFavs()
        }
    }

    /**
     *  Get a list of saved country from database and update favList
     */

    fun getAllCountryFavs() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favList.value = cfdao.getAllFavCountries()
        }
    }
}