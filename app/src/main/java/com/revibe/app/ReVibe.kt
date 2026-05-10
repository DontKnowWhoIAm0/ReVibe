package com.revibe.app

import android.app.Application
import com.revibe.app.di.AppComponent
import com.revibe.app.di.AppModule
import com.revibe.app.di.DaggerAppComponent
import com.revibe.core.network.di.DaggerNetworkComponent
import com.revibe.core.network.di.NetworkComponent

class ReVibe : Application() {

    lateinit var appComponent: AppComponent
    lateinit var networkComponent: NetworkComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory()
            .create(AppModule(this, "http://localhost:8080/"))

        networkComponent = DaggerNetworkComponent.factory()
            .create(appComponent)
    }
}