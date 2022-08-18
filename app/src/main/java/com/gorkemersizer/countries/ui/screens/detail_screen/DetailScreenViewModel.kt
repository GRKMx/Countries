package com.gorkemersizer.countries.ui.screens.detail_screen

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gorkemersizer.countries.data.entity.CountryDetail
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import com.gorkemersizer.countries.util.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(var crepo: CountriesDaoRepo): ViewModel() {
    var countryDetail = MutableLiveData<CountryDetail>()

    init {
        countryDetail = crepo.getTheCountryDetail()
    }

    fun getCountry(countryCode: String) {
        crepo.getCountry(countryCode)
    }
}