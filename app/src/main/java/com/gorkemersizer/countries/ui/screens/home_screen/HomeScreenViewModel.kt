package com.gorkemersizer.countries.ui.screens.home_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorkemersizer.countries.data.entity.Country
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var countryList = MutableLiveData<List<Country>>()
    init {
        getAllCountries()
        countryList = crepo.getCountries()
    }

    fun getAllCountries() {
        crepo.getAllCountries()
    }
}