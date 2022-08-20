package com.gorkemersizer.countries.data.repo

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import com.gorkemersizer.countries.data.entity.*
import com.gorkemersizer.countries.retrofit.CountriesDao
import com.gorkemersizer.countries.room.CountryFavsDao
import com.gorkemersizer.countries.util.Constants.API_KEY
import com.gorkemersizer.countries.util.Constants.LIMIT
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesDaoRepo(var cdao: CountriesDao, var cfdao: CountryFavsDao) {
    var countryList: MutableLiveData<List<Country>> = MutableLiveData()
    var countryDetail: MutableLiveData<CountryDetail> = MutableLiveData()
    var favList: MutableLiveData<List<CountryFav>> = MutableLiveData()

    /**
     *  Countries ----------------------------------------------------------------------------------
     */

    /**
     *  Returns list of country
     */

    fun getCountries() : MutableLiveData<List<Country>> {
        return countryList
    }

    /**
     *  Returns the country detail
     */

    fun getTheCountryDetail() : MutableLiveData<CountryDetail> {
        return countryDetail
    }

    /**
     *  Get list of country by api
     */

    //suspend fun getAllCountries() = cdao.getCountries(API_KEY, LIMIT)

    suspend fun getAllCountries(): CountryResponse {
        Log.d("daorepo","daorepo all country çalıştı")
        return cdao.getCountries(API_KEY, LIMIT)
    }

/*
    fun getAllCountries() {
        cdao.getCountries(API_KEY, LIMIT).enqueue(object: Callback<CountryResponse>{
            override fun onResponse(
                call: Call<CountryResponse>,
                response: Response<CountryResponse>
            ) {
                if (response.body()?.data != null) {
                    val list = response.body()!!.data
                    countryList.value = list
                }
            }
            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {}
        })
    }

 */



    /**
     *  Get the country by api
     */

    //suspend fun getCountry(countryCode: String) = cdao.getCountryDetail(countryCode, API_KEY)
/*
    suspend fun getCountry(): CountryDetailResponse {
        return cdao.getCountryDetail(API_KEY, LIMIT)
    }

 */

    suspend fun getCountry(countryCode: String): CountryDetailResponse {
        Log.d("daorepo","daorepo detail country çalıştı")
        return cdao.getCountryDetail(countryCode, API_KEY)
    }

/*
    fun getCountry(countryCode: String) {
            cdao.getCountryDetail(countryCode, API_KEY).enqueue(object: Callback<CountryDetailResponse>{
                override fun onResponse(
                    call: Call<CountryDetailResponse>,
                    response: Response<CountryDetailResponse>
                ) {
                    if (response.body()?.data != null) {
                        val detail = response.body()!!.data
                        countryDetail.value = detail
                    }
                }
                override fun onFailure(call: Call<CountryDetailResponse>, t: Throwable) {}
            })
    }

 */


    /**
     *   Saved Countries ---------------------------------------------------------------------------
     */

    fun getCountryFavList() : MutableLiveData<List<CountryFav>> {
        return favList
    }

    /**
     *  Save the country to database
     */

    fun addCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newCountryFav = CountryFav(code, name)
            cfdao.addCountryFav(newCountryFav)
            getAllCountryFavs()
        }
    }

    /**
     *  Delete the saved country from database
     */

    fun deleteCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val deletedCountryFav = CountryFav(code, name)
            cfdao.deleteCountryFav(deletedCountryFav)
            getAllCountryFavs()
        }
    }

    /**
     *  Get list of saved country from database
     */

    fun getAllCountryFavs() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favList.value = cfdao.getAllFavCountries()
        }
    }
}