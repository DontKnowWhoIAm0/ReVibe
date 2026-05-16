package com.revibe.core.network.di

import okhttp3.Interceptor
import okhttp3.Response
import com.revibe.core.data.local.TokenDataStore
import kotlinx.coroutines.runBlocking

class AuthInterceptor(
    private val dataStore: TokenDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.getToken() }

        val request = chain.request().newBuilder()
            .apply {
                if (token != null) addHeader("Authorization", "Bearer $token")
            }
            .build()

        return chain.proceed(request)
    }
}