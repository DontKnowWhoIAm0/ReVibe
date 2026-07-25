package com.revibe.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class TokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val KEY_TOKEN = stringPreferencesKey("auth_token")
    private val KEY_USER_ID = stringPreferencesKey("user_id")
    private val KEY_USER_NAME = stringPreferencesKey("user_name")

    suspend fun saveToken(token: String) {
        dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun saveUserId(userId: String) {
        dataStore.edit { it[KEY_USER_ID] = userId }
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { it[KEY_USER_NAME] = userName }
    }

    suspend fun getToken(): String? = dataStore.data.map { it[KEY_TOKEN] }.first()

    val tokenFlow: Flow<String?> = dataStore.data.map { it[KEY_TOKEN] }

    suspend fun getUserId(): String? = dataStore.data.map { it[KEY_USER_ID] }.first()

    suspend fun getUserName(): String? = dataStore.data.map { it[KEY_USER_NAME] }.first()

}