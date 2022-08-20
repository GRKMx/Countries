package com.gorkemersizer.countries.ui.screens.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gorkemersizer.countries.data.entity.Country
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import com.gorkemersizer.countries.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var countryList = MutableLiveData<List<Country>>()
    var favList = MutableLiveData<List<CountryFav>>()

    init {
        //getAllCountries()
        //countryList = crepo.getCountries()
        getFavList()
        favList = crepo.getCountryFavList()
    }

    /**
     * Present list of all countries
     */

    fun getAllCountries() = liveData (Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(crepo.getAllCountries()))
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred!", null))
        }
    }



/*
    fun getAllCountries() {
        crepo.getAllCountries()
    }

 */



    /**
     * Get list of saved country from database
     */

    fun getFavList() {
        crepo.getAllCountryFavs()
    }

    /**
     *  Save the country to database
     */

    fun addCountryToFav(code: String, name: String) {
        crepo.addCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }

    /**
     *  Delete the saved country from database
     */

    fun deleteCountryFromFav(code: String, name: String) {
        crepo.deleteCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }
}