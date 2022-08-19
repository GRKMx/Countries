package com.gorkemersizer.countries.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gorkemersizer.countries.R
import com.gorkemersizer.countries.data.entity.Country
import com.gorkemersizer.countries.data.entity.CountryFav
import com.gorkemersizer.countries.databinding.CountryCardDesignBinding
import com.gorkemersizer.countries.ui.screens.home_screen.HomeScreenDirections
import com.gorkemersizer.countries.ui.screens.home_screen.HomeScreenViewModel
import java.lang.Exception

class CountryAdapter(
    var mContext: Context,
    var countryList: List<Country>,
    var viewModel: HomeScreenViewModel
): RecyclerView.Adapter<CountryAdapter.CardViewHolder>() {
    inner class CardViewHolder(binding: CountryCardDesignBinding): RecyclerView.ViewHolder(binding.root) {
        var binding: CountryCardDesignBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding: CountryCardDesignBinding = DataBindingUtil.inflate(layoutInflater, R.layout.country_card_design, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val country = countryList[position]
        val t =holder.binding
        t.countryObject = country

        /**
         * Set the fav icon color
         */

        if (viewModel.favList.value!!.contains(CountryFav(country.code!!, country.name!!))){
            t.imageView.setImageResource(R.drawable.ic_star_black)
        }
        else if (!viewModel.favList.value!!.contains(CountryFav(country.code, country.name))){
            t.imageView.setImageResource(R.drawable.ic_star_gray)
        }

        /**
         *  Navigate to detail screen when a country clicked
         */

        t.cardRow.setOnClickListener {
            val code = country.code.toString()
            val action = HomeScreenDirections.actionHomeScreenToDetailScreen(code)
            try {
                Navigation.findNavController(it).navigate(action)
            }catch (e: Exception){}
        }

        /**
         * Add or Delete a country to/from favourites when icon clicked
         */

        t.imageView.setOnClickListener {
            if (viewModel.favList.value!!.contains(CountryFav(country.code, country.name))){
                viewModel.deleteCountryFromFav(country.code, country.name)
                t.imageView.setImageResource(R.drawable.ic_star_gray)
            } else {
                viewModel.addCountryToFav(country.code, country.name)
                t.imageView.setImageResource(R.drawable.ic_star_black)
            }
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }
}