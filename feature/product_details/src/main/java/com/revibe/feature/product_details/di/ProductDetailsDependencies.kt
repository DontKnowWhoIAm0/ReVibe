package com.revibe.feature.product_details.di

import android.content.Context
import retrofit2.Retrofit

interface ProductDetailsDependencies {
    fun retrofit(): Retrofit
    fun context(): Context
}