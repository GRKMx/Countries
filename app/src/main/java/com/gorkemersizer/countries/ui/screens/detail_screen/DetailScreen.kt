package com.gorkemersizer.countries.ui.screens.detail_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.databinding.FragmentDetailScreenBinding
import com.gorkemersizer.countries.ui.screens.home_screen.HomeScreenDirections
import com.gorkemersizer.countries.util.Constants.WIKI_URL
import com.gorkemersizer.countries.util.downloadFromUrl
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailScreen : Fragment() {
    private lateinit var binding: FragmentDetailScreenBinding
    private lateinit var viewModel: DetailScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_screen, container, false)

        viewModel.countryDetail.observe(viewLifecycleOwner) {
            binding.countryDetailObject = viewModel.countryDetail.value
            binding.imageViewCountry.downloadFromUrl(it.flagImageUri)
        }
        binding.detailScreenFragment = this
        binding.buttonForMoreInfo.setOnClickListener {
            val i =  Intent(Intent.ACTION_VIEW, Uri.parse(WIKI_URL+viewModel.countryDetail.value!!.wikiDataId))
            startActivity(i)
        }
// --------------
        /*
        val countryFromDetail = viewModel.countryDetail.value
        val countryCode = countryFromDetail!!.code!!
        val countryName = countryFromDetail.name!!
        if (viewModel.favList.value!!.contains(CountryFav(countryCode, countryName))){
            binding.imageViewFavButton.setImageResource(R.drawable.ic_star_black)
        }
        else if (!viewModel.favList.value!!.contains(CountryFav(countryCode, countryName))){
            binding.imageViewFavButton.setImageResource(R.drawable.ic_star_gray)
        }

         */

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
//---------------
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val code = DetailScreenArgs.fromBundle(it).code.toString()
            viewModel.getCountry(code)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailScreenViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun backButtonClicked(v: View) {
        parentFragmentManager.popBackStack()
    }
}