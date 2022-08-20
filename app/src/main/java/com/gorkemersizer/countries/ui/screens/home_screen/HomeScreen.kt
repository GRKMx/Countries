package com.gorkemersizer.countries.ui.screens.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.databinding.FragmentHomeScreenBinding
import com.gorkemersizer.countries.ui.adapters.CountryAdapter
import com.gorkemersizer.countries.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var viewModel: HomeScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.homeScreenFragment = this

        /**
         * Present list of all countries
         */

        observeData()

        viewModel.countryList.observe(viewLifecycleOwner) {
            val adapter = CountryAdapter(requireContext(), it, viewModel)
            binding.countriesAdapter = adapter
        }


        return binding.root
    }



    fun observeData() {
        viewModel.getAllCountries().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { country ->
                        val adapter = CountryAdapter(requireContext(), country.data, viewModel)
                        binding.countriesAdapter = adapter
                    }
                    //binding.rvCountries.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message+" Too many request! Please click slower", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {}
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeScreenViewModel by viewModels()
        viewModel = tempViewModel
    }
}