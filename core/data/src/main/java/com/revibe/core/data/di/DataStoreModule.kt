package com.revibe.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class DataStoreModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDataStore(): DataStore<Preferences> = context.dataStore
}