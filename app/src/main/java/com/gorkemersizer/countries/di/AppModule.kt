package com.gorkemersizer.countries.di

import android.content.Context
import androidx.room.Room
import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import com.gorkemersizer.countries.retrofit.ApiUtils
import com.gorkemersizer.countries.retrofit.CountriesDao
import com.gorkemersizer.countries.room.CountryFavsDao
import com.gorkemersizer.countries.room.CountryFavsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCountryDaoRepo(cdao: CountriesDao, cfdao: CountryFavsDao): CountriesDaoRepo {
        return CountriesDaoRepo(cdao, cfdao)
    }

    @Provides
    @Singleton
    fun provideCountriesDao(): CountriesDao {
        return ApiUtils.getCountriesDao()
    }

    @Provides
    @Singleton
    fun provideCountryFavsDao(@ApplicationContext context: Context) : CountryFavsDao {
        val db = Room.databaseBuilder(context,CountryFavsDatabase::class.java,"countryfavs.sqlite")
            .fallbackToDestructiveMigration()
            .build()
        return  db.getCountryFavsDao()
    }
}