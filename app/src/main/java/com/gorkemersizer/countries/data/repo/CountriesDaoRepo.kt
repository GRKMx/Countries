package com.gorkemersizer.countries.data.repo

import androidx.lifecycle.MutableLiveData
import com.gorkemersizer.countries.data.entity.*
import com.gorkemersizer.countries.retrofit.CountriesDao
import com.gorkemersizer.countries.room.CountryFavsDao
import com.gorkemersizer.countries.util.Constants.API_KEY
import com.gorkemersizer.countries.util.Constants.LIMIT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesDaoRepo(var cdao: CountriesDao, var cfdao: CountryFavsDao) {
    var countryList: MutableLiveData<List<Country>> = MutableLiveData()
    var countryDetail: MutableLiveData<CountryDetail> = MutableLiveData()
    var favList: MutableLiveData<List<CountryFav>> = MutableLiveData()

    fun getCountries() : MutableLiveData<List<Country>> {
        return countryList
    }

    fun getTheCountryDetail() : MutableLiveData<CountryDetail> {
        return countryDetail
    }

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

    // ------------------------------------------------------------------------------------------------------------------------

    fun getCountryFavList() : MutableLiveData<List<CountryFav>> {
        return favList
    }

    fun addCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newCountryFav = CountryFav(code, name)
            cfdao.addCountryFav(newCountryFav)
            getAllCountryFavs()
        }
    }

    fun deleteCountryFav(code: String, name: String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val deletedCountryFav = CountryFav(code, name)
            cfdao.deleteCountryFav(deletedCountryFav)
            getAllCountryFavs()
        }
    }

    fun getAllCountryFavs() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            favList.value = cfdao.getAllFavCountries()
        }
    }
}