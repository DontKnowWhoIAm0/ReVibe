package com.revibe.feature.catalog.di

import com.revibe.core.network.favourites.FavouritesApiService
import com.revibe.feature.catalog.data.CatalogApiService
import com.revibe.feature.catalog.data.CatalogRepository
import com.revibe.feature.catalog.data.impl.CatalogRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object CatalogModule {

    @Provides
    fun provideCatalogApiService(retrofit: Retrofit): CatalogApiService =
        retrofit.create(CatalogApiService::class.java)

    @Provides
    fun provideCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository = impl

    @Provides
    fun provideFavouritesApiService(retrofit: Retrofit): FavouritesApiService =
        retrofit.create(FavouritesApiService::class.java)
}