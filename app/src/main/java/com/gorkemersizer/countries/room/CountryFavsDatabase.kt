package com.gorkemersizer.countries.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorkemersizer.countries.data.entity.CountryFav

@Database(entities = [CountryFav::class], version = 2)
abstract class CountryFavsDatabase : RoomDatabase() {
    abstract fun getCountryFavsDao(): CountryFavsDao
}