package com.revibe.core.network.di

import com.revibe.core.network.ApiService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun apiService(): ApiService

    @Component.Factory
    interface Factory {
        fun create(networkModule: NetworkModule): NetworkComponent
    }
}