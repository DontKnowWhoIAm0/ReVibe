package com.revibe.feature.catalog.di

import retrofit2.Retrofit

interface CatalogDependencies {
    fun retrofit(): Retrofit
}