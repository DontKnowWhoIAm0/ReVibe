package com.revibe.feature.login.di

import com.revibe.feature.login.data.LoginApiService
import com.revibe.feature.login.data.LoginRepository
import com.revibe.feature.login.data.impl.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object LoginModule {

    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService =
        retrofit.create(LoginApiService::class.java)

    @Provides
    fun provideLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository = impl
}