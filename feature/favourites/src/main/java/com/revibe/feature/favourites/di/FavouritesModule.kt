package com.revibe.feature.favourites.di

import android.content.Context
import com.revibe.core.db.ReVibeDatabase
import com.revibe.core.db.dao.CartDao
import com.revibe.core.network.favourites.FavouritesApiService
import com.revibe.feature.favourites.data.FavouritesRepository
import com.revibe.feature.favourites.data.impl.FavouritesRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object FavouritesModule {

    @Provides
    fun provideFavouritesApiService(retrofit: Retrofit): FavouritesApiService =
        retrofit.create(FavouritesApiService::class.java)

    @Provides
    fun provideFavouritesRepository(impl: FavouritesRepositoryImpl): FavouritesRepository = impl

    @Provides
    fun provideCartDao(context: Context): CartDao = ReVibeDatabase.getInstance(context).cartDao()
}