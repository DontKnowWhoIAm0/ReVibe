package com.revibe.feature.product_details.di

import com.revibe.core.network.favourites.FavouritesApiService
import com.revibe.feature.product_details.data.ProductDetailsApiService
import com.revibe.feature.product_details.data.ProductDetailsRepository
import com.revibe.feature.product_details.data.impl.ProductDetailsRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object ProductDetailsModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): ProductDetailsApiService =
        retrofit.create(ProductDetailsApiService::class.java)

    @Provides
    fun provideRepository(impl: ProductDetailsRepositoryImpl): ProductDetailsRepository = impl

    @Provides
    fun provideFavouritesApiService(retrofit: Retrofit): FavouritesApiService =
        retrofit.create(FavouritesApiService::class.java)
}