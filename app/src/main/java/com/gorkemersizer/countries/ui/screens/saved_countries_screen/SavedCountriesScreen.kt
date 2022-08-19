package com.gorkemersizer.countries.ui.screens.saved_countries_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.databinding.FragmentSavedCountriesScreenBinding
import com.gorkemersizer.countries.ui.adapters.CountryFavAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedCountriesScreen : Fragment() {
    private lateinit var binding: FragmentSavedCountriesScreenBinding
    private lateinit var viewModel: SavedCountriesScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_countries_screen, container, false)
        binding.savedCountriesFragment = this

        /**
         * Present list of saved countries
         */

        viewModel.favList.observe(viewLifecycleOwner) {
            val adapter = CountryFavAdapter(requireContext(), it, viewModel)
            binding.countryFavsAdapter = adapter
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SavedCountriesScreenViewModel by viewModels()
        viewModel = tempViewModel
    }
}