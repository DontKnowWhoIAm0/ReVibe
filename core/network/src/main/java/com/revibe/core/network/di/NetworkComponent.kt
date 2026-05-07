package com.revibe.core.network.di

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class], dependencies = [NetworkDependencies::class])
interface NetworkComponent {
    fun retrofit(): Retrofit

    @Component.Factory
    interface Factory {
        fun create(dependencies: NetworkDependencies): NetworkComponent
    }
}