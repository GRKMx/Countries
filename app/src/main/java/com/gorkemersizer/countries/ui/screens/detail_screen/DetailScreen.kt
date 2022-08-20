package com.gorkemersizer.countries.ui.screens.detail_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.navigation.Navigation
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.databinding.FragmentDetailScreenBinding
import com.gorkemersizer.countries.ui.adapters.CountryAdapter
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

        /*
        viewModel.countryDetail.observe(viewLifecycleOwner) {

            /**
             * Set the fav icon color
             */

            if (viewModel.favList.value!!.contains(CountryFav(it.code!!, it.name!!))){
                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_black)
            }
            else if (!viewModel.favList.value!!.contains(CountryFav(it.code, it.name))){
                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_gray)
            }
            binding.countryDetailObject = viewModel.countryDetail.value
            binding.imageViewCountry.downloadFromUrl(it?.flagImageUri)
        }

         */
        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            observeData(code)
        }

        binding.detailScreenFragment = this

        /**
         * Navigate to wikidata when button clicked
         */
/*
        binding.buttonForMoreInfo.setOnClickListener {
            val i =  Intent(Intent.ACTION_VIEW, Uri.parse(WIKI_URL+viewModel.countryDetail.value!!.wikiDataId))
            startActivity(i)
        }

 */

        /**
         * Add or Delete a country to/from favourites item when icon clicked
         */
/*
        binding.imageViewFavButton.setOnClickListener {
            val countryFromDetail = viewModel.countryDetail.value
            val countryCode = countryFromDetail!!.code!!
            val countryName = countryFromDetail.name!!
            if (viewModel.favList.value!!.contains(CountryFav(countryCode, countryName))){
                viewModel.deleteCountryFromFav(countryCode, countryName)
                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_gray)
            } else {
                viewModel.addCountryToFav(countryCode, countryName)
                binding.imageViewFavButton.setImageResource(R.drawable.ic_star_black)
            }
        }

 */
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            observeData(code)
        }

 */
    }

/*
        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            viewModel.getCountry(code)
        }
    }

 */

    fun observeData(code: String) {
        viewModel.getCountry(code).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
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
                        //__
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
                        binding.buttonForMoreInfo.setOnClickListener {
                            val i =  Intent(Intent.ACTION_VIEW, Uri.parse(WIKI_URL+country.data.wikiDataId.toString()))
                            startActivity(i)
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message + "Too many request! Please click slower", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {}
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailScreenViewModel by viewModels()
        viewModel = tempViewModel
    }

    /**
     * Navigate home page when button clicked
     */

    fun backButtonClicked(v: View) {
        val action = DetailScreenDirections.actionDetailScreenToHomeScreen()
        Navigation.findNavController(v).navigate(action)
    }
}