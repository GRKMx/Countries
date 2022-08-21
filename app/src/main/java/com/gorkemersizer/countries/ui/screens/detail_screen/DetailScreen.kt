package com.gorkemersizer.countries.ui.screens.detail_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.navigation.Navigation
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.databinding.FragmentDetailScreenBinding
import com.gorkemersizer.countries.util.Constants.REQUEST_TIME
import com.gorkemersizer.countries.util.Constants.WIKI_URL
import com.gorkemersizer.countries.util.Status
import com.gorkemersizer.countries.util.downloadFromUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreen : Fragment() {
    private lateinit var binding: FragmentDetailScreenBinding
    private lateinit var viewModel: DetailScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_screen, container, false)
        binding.detailScreenFragment = this

        /**
         * Refresh screen when swiped
         */

        binding.swipeRefreshLayoutDetailScreen.setOnRefreshListener {
            arguments?.let {
                val code = DetailScreenArgs.fromBundle(it).code.toString()
                observeData(code)
            }
            binding.swipeRefreshLayoutDetailScreen.isRefreshing = false
        }

        /**
         * Get code of clicked country and run observeData
         */

        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            observeData(code)
        }

        return binding.root
    }

    /**
     * Refresh screen
     */

    fun refreshData() {
        binding.countryLoading.visibility = View.VISIBLE
        binding.buttonForMoreInfo.visibility = View.GONE
        binding.textViewCD.visibility = View.GONE
        Thread.sleep(REQUEST_TIME) // Prevent 429 - Too many request error caused by api
        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            observeData(code)
        }
    }

    /**
     * Observe a country
     */

    fun observeData(code: String) {
        viewModel.getCountry(code).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.buttonForMoreInfo.visibility = View.VISIBLE
                    binding.textViewCD.visibility = View.VISIBLE
                    binding.imageViewBackButton.visibility = View.VISIBLE
                    binding.imageViewFavButton.visibility = View.VISIBLE
                    /**
                     * Get a country detail
                     */
                    it.data?.let { country ->
                        viewModel.getCountry(country.data.code.toString())
                        binding.countryDetailObject = country.data
                        binding.imageViewCountry.downloadFromUrl(country.data.flagImageUri)

                        if (viewModel.favList.value!!.contains(CountryFav(country.data.code!!, country.data.name!!))){
                            binding.imageViewFavButton.setImageResource(R.drawable.ic_star_black)
                        }
                        else if (!viewModel.favList.value!!.contains(CountryFav(country.data.code, country.data.name))){
                            binding.imageViewFavButton.setImageResource(R.drawable.ic_star_gray)
                        }
                        /**
                         * Add/Delete a country to/from favourites
                         */
                        binding.imageViewFavButton.setOnClickListener {
                            val countryCode = country.data.code
                            val countryName = country.data.name
                            if (viewModel.favList.value!!.contains(CountryFav(countryCode, countryName))){
                                viewModel.deleteCountryFromFav(countryCode, countryName)
                                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_gray)
                            } else {
                                viewModel.addCountryToFav(countryCode, countryName)
                                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_black)
                            }
                        }
                        /**
                         * Navigate to WikiData
                         */
                        binding.buttonForMoreInfo.setOnClickListener {
                            val i =  Intent(Intent.ACTION_VIEW, Uri.parse(WIKI_URL+country.data.wikiDataId.toString()))
                            startActivity(i)
                        }
                    }
                    binding.countryLoading.visibility = View.GONE
                    binding.textViewCD.visibility = View.VISIBLE
                    binding.buttonForMoreInfo.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    /**
                     * Try to get the data with delay
                     */
                    refreshData()
                }
                Status.LOADING -> {
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.buttonForMoreInfo.visibility = View.GONE
                    binding.textViewCD.visibility = View.GONE
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailScreenViewModel by viewModels()
        viewModel = tempViewModel
    }

    /**
     * Navigate to home page
     */

    fun backButtonClicked(v: View) {
        val action = DetailScreenDirections.actionDetailScreenToHomeScreen()
        Navigation.findNavController(v).navigate(action)
    }
}