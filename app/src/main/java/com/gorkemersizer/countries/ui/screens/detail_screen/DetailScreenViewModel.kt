package com.gorkemersizer.countries.ui.screens.detail_screen


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorkemersizer.countries.data.entity.CountryDetail
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var countryDetail = MutableLiveData<CountryDetail>()
    var favList = MutableLiveData<List<CountryFav>>()

    init {
        countryDetail = crepo.getTheCountryDetail()
        getFavList()
        favList = crepo.getCountryFavList()
    }

    fun getCountry(countryCode: String) {
        crepo.getCountry(countryCode)
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