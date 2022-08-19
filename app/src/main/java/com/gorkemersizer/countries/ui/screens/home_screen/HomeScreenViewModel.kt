package com.gorkemersizer.countries.ui.screens.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkemersizer.countries.data.entity.Country
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var countryList = MutableLiveData<List<Country>>()
    var favList = MutableLiveData<List<CountryFav>>()
    init {
        getAllCountries()
        countryList = crepo.getCountries()
        getFavList()
        favList = crepo.getCountryFavList()
    }

    fun getAllCountries() {
        crepo.getAllCountries()
    }

    fun getFavList() {
        crepo.getAllCountryFavs()
    }

    fun addCountryToFav(code: String, name: String) {
        crepo.addCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }

    fun deleteCountryFromFav(code: String, name: String) {
        crepo.deleteCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }
}