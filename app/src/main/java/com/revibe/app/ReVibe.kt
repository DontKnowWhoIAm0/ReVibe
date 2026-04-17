package com.revibe.app

import android.app.Application
import com.revibe.core.network.di.NetworkModule
import com.revibe.core.network.ApiService
import com.revibe.core.network.di.DaggerNetworkComponent

class ReVibe : Application() {

    lateinit var apiService: ApiService
        private set

    override fun onCreate() {
        super.onCreate()

        val networkComponent = DaggerNetworkComponent.factory()
            .create(NetworkModule("")) // ссылка

        apiService = networkComponent.apiService()
    }
}