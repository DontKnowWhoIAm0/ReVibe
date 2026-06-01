package com.revibe.feature.cart.di

import android.content.Context
import retrofit2.Retrofit

interface CartDependencies {
    fun retrofit(): Retrofit
    fun context(): Context
}
