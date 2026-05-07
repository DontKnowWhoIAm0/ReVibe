package com.revibe.app.di

import android.app.Application
import com.revibe.core.network.di.NetworkDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : NetworkDependencies {
    fun application(): Application
    override fun baseUrl(): String

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}