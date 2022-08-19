package com.gorkemersizer.countries.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.databinding.CountryfavCardDesignBinding
import com.gorkemersizer.countries.ui.screens.saved_countries_screen.SavedCountriesScreenDirections
import com.gorkemersizer.countries.ui.screens.saved_countries_screen.SavedCountriesScreenViewModel

class CountryFavAdapter(
    var mContext: Context,
    var favList: List<CountryFav>,
    var viewModel: SavedCountriesScreenViewModel
): RecyclerView.Adapter<CountryFavAdapter.CardViewHolder>() {
    inner class CardViewHolder(binding: CountryfavCardDesignBinding): RecyclerView.ViewHolder(binding.root) {
        var binding: CountryfavCardDesignBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding: CountryfavCardDesignBinding = DataBindingUtil.inflate(layoutInflater, R.layout.countryfav_card_design, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val country = favList[position]
        val t = holder.binding
        t.countryFavObject = country

        /**
         * Delete a country from favourites when icon clicked
         */

        t.imageView.setOnClickListener {
            viewModel.deleteCountryFromFav(country.code, country.name)
        }

        /**
         *  Navigate to detail screen when a country clicked
         */

        t.cardRow.setOnClickListener {
            val code = country.code
            val action = SavedCountriesScreenDirections.actionSavedCountriesScreenToDetailScreen(code)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return favList.size
    }

}