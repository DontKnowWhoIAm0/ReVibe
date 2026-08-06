package com.revibe.feature.catalog.di

import android.content.Context
import retrofit2.Retrofit

interface CatalogDependencies {
    fun retrofit(): Retrofit
    fun context(): Context
}
