package com.gorkemersizer.countries.retrofit

import com.gorkemersizer.countries.util.Constants.BASE_URL

class ApiUtils {
    companion object {
        fun getCountriesDao() : CountriesDao {
            return RetrofitClient.getClient(BASE_URL).create(CountriesDao::class.java)
        }
    }
}