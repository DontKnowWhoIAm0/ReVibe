package com.revibe.feature.favourites.di

import android.content.Context
import retrofit2.Retrofit

interface FavouritesDependencies {
    fun retrofit(): Retrofit
    fun context(): Context
}