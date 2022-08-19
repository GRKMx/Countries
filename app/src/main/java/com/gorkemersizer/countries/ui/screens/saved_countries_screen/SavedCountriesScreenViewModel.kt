package com.gorkemersizer.countries.ui.screens.saved_countries_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedCountriesScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var favList = MutableLiveData<List<CountryFav>>()
    init {
        getFavList()
        favList = crepo.getCountryFavList()
    }

    fun getFavList() {
        crepo.getAllCountryFavs()
    }

    fun deleteCountryFromFav(code: String, name: String) {
        crepo.deleteCountryFav(code, name)
        favList = crepo.getCountryFavList()
    }
}