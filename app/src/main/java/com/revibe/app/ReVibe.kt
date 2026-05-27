package com.revibe.app

import android.app.Application
import com.revibe.app.di.AppComponent
import com.revibe.app.di.AppModule
import com.revibe.app.di.DaggerAppComponent
import com.revibe.core.data.di.DataStoreModule
import com.revibe.core.data.local.TokenDataStore
import com.revibe.core.network.di.DaggerNetworkComponent
import com.revibe.core.network.di.NetworkComponent

class ReVibe : Application() {

    lateinit var appComponent: AppComponent
    lateinit var networkComponent: NetworkComponent
    lateinit var dataStoreModule: DataStoreModule
    val tokenDataStore get() = networkComponent.tokenDataStore()

    override fun onCreate() {
        super.onCreate()

        dataStoreModule = DataStoreModule(this)

        appComponent = DaggerAppComponent.factory()
            .create(AppModule(this, "http://localhost:8080/"))

        networkComponent = DaggerNetworkComponent.factory()
            .create(
                dependencies = appComponent,
                dataStoreModule = dataStoreModule
            )
    }
}