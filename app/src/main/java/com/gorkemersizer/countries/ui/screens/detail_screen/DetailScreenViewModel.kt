package com.gorkemersizer.countries.ui.screens.detail_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import com.gorkemersizer.countries.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {

    var favList = MutableLiveData<List<CountryFav>>()

    init {
        getFavList()
        favList = crepo.getCountryFavList()
    }

    /**
     * Get a country with details by api
     */

    fun getCountry(countryCode: String) = liveData (Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(crepo.getCountry(countryCode)))
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error!",null))
        }
    }

    /**
     * Get a list of saved countries from database
     */

    fun getFavList() {
        crepo.getAllCountryFavs()
    }

    /**
     * Save the country to favourites
     */

    fun addCountryToFav(code: String, name: String) {
        crepo.addCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }

    /**
     * Delete the country from favourites
     */

    fun deleteCountryFromFav(code: String, name: String) {
        crepo.deleteCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }
}