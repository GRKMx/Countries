package com.gorkemersizer.countries.data.repo

import androidx.lifecycle.MutableLiveData
import com.gorkemersizer.countries.data.entity.Country
import com.gorkemersizer.countries.data.entity.CountryDetail
import com.gorkemersizer.countries.data.entity.CountryDetailResponse
import com.gorkemersizer.countries.data.entity.CountryResponse
import com.gorkemersizer.countries.retrofit.CountriesDao
import com.gorkemersizer.countries.util.Constants.API_KEY
import com.gorkemersizer.countries.util.Constants.LIMIT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesDaoRepo(var cdao: CountriesDao) {
    var countryList: MutableLiveData<List<Country>> = MutableLiveData()
    var countryDetail: MutableLiveData<CountryDetail> = MutableLiveData()

    fun getCountries() : MutableLiveData<List<Country>> {
        return countryList
    }

    fun getTheCountryDetail() : MutableLiveData<CountryDetail> {
        return countryDetail
    }

    fun getAllCountries() {
        cdao.getCountries(API_KEY, LIMIT).enqueue(object: Callback<CountryResponse>{
            override fun onResponse(call: Call<CountryResponse>?, response: Response<CountryResponse>) {
                val list = response.body()!!.data
                countryList.value = list
            }
            override fun onFailure(call: Call<CountryResponse>?, t: Throwable?) {}
        })
    }

    fun getCountry(countryCode: String) {
        cdao.getCountryDetail(countryCode, API_KEY).enqueue(object: Callback<CountryDetailResponse>{
            override fun onResponse(call: Call<CountryDetailResponse>?, response: Response<CountryDetailResponse>) {
                val detail = response.body()!!.data
                countryDetail.value = detail
            }
            override fun onFailure(call: Call<CountryDetailResponse>?, t: Throwable?) {}
        })
    }
}