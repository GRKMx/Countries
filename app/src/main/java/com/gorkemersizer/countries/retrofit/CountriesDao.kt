package com.gorkemersizer.countries.retrofit

import com.gorkemersizer.countries.data.entity.CountryDetailResponse
import com.gorkemersizer.countries.data.entity.CountryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesDao {
/*
    @GET("countries")
    fun getCountries(
        @Header("X-RapidAPI-Key") key: String,
        @Query("limit") limit: String
    ): Call<CountryResponse>

    @GET("countries/{countryCode}")
    fun getCountryDetail(
        @Path("countryCode") countryCode: String,
        @Header("X-RapidAPI-Key") key: String
    ): Call<CountryDetailResponse>
 */

/*

 */
    @GET("countries")
    suspend fun getCountries(
        @Header("X-RapidAPI-Key") key: String,
        @Query("limit") limit: String
    ): CountryResponse



    @GET("countries/{countryCode}")
    suspend fun getCountryDetail(
        @Path("countryCode") countryCode: String,
        @Header("X-RapidAPI-Key") key: String
    ): CountryDetailResponse




}