package com.revibe.feature.login.di

import retrofit2.Retrofit

interface LoginDependencies {
    fun retrofit(): Retrofit
}