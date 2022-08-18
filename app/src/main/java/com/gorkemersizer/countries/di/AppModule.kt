package com.gorkemersizer.countries.di

import com.gorkemersizer.countries.data.repo.CountriesDaoRepo
import com.gorkemersizer.countries.retrofit.ApiUtils
import com.gorkemersizer.countries.retrofit.CountriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCountryDaoRepo(cdao: CountriesDao): CountriesDaoRepo {
        return CountriesDaoRepo(cdao)
    }

    @Provides
    @Singleton
    fun provideCountriesDao(): CountriesDao {
        return ApiUtils.getCountriesDao()
    }
}