package com.revibe.feature.registration.di

import com.revibe.feature.registration.data.RegistrationApiService
import com.revibe.feature.registration.data.RegistrationRepository
import com.revibe.feature.registration.data.impl.RegistrationRepositoryImpl
import com.revibe.feature.registration.domain.usecase.GetMeUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object RegistrationModule {

    @Provides
    fun provideRegistrationApiService(retrofit: Retrofit): RegistrationApiService =
        retrofit.create(RegistrationApiService::class.java)

    @Provides
    fun provideRegistrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository = impl

    @Provides
    fun provideGetMeUseCase(apiService: RegistrationApiService): GetMeUseCase = GetMeUseCase(apiService)
}