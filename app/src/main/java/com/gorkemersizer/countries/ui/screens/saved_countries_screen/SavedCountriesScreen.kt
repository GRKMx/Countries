package com.gorkemersizer.countries.ui.screens.saved_countries_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gorkemersizer.countries.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedCountriesScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_countries_screen, container, false)
    }
}