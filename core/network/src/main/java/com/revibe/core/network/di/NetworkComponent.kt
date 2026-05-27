package com.revibe.core.network.di

import com.revibe.core.data.di.DataStoreModule
import com.revibe.core.data.local.TokenDataStore
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataStoreModule::class], dependencies = [NetworkDependencies::class])
interface NetworkComponent {
    fun retrofit(): Retrofit
    fun tokenDataStore(): TokenDataStore

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: NetworkDependencies,
            dataStoreModule: DataStoreModule
        ): NetworkComponent
    }
}