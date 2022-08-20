package com.gorkemersizer.countries.ui.screens.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.databinding.FragmentHomeScreenBinding
import com.gorkemersizer.countries.ui.adapters.CountryAdapter
import com.gorkemersizer.countries.util.Constants.REQUEST_TIME
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
         * Refresh screen when swiped
         */

        binding.swipeRefreshLayoutHomeScreen.setOnRefreshListener {
            observeData()
            binding.swipeRefreshLayoutHomeScreen.isRefreshing = false
        }

        observeData()
        return binding.root
    }

    /**
     * Refresh screen
     */

    fun refreshData() {
        binding.countriesLoading.visibility = View.VISIBLE
        Thread.sleep(REQUEST_TIME) // Prevent 429 - Too many request error caused by api
        observeData()
    }

    /**
     * Observe countries
     */

    fun observeData() {
        viewModel.getAllCountries().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    /**
                     * Get a list of countries and set adapter
                     */
                    it.data?.let { country ->
                        val adapter = CountryAdapter(requireContext(), country.data, viewModel)
                        binding.countriesAdapter = adapter
                    }
                    binding.countriesLoading.visibility = View.GONE
                }
                Status.ERROR -> {
                    /**
                     * Try to get the data with delay
                     */
                    refreshData()
                }
                Status.LOADING -> {
                    binding.countriesLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeScreenViewModel by viewModels()
        viewModel = tempViewModel
    }
}