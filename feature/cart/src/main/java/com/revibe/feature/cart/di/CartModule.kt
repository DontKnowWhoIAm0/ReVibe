package com.revibe.feature.cart.di

import android.content.Context
import com.revibe.core.db.ReVibeDatabase
import com.revibe.core.db.dao.CartDao
import com.revibe.feature.cart.data.CartApiService
import com.revibe.feature.cart.data.CartRepository
import com.revibe.feature.cart.data.impl.CartRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object CartModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ReVibeDatabase = ReVibeDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideCartDao(db: ReVibeDatabase): CartDao = db.cartDao()

    @Provides
    fun provideCartApiService(retrofit: Retrofit): CartApiService = retrofit.create(CartApiService::class.java)

    @Provides
    @Singleton
    fun provideCartRepository(impl: CartRepositoryImpl): CartRepository = impl
}
