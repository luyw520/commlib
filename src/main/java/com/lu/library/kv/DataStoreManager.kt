package com.lu.library.kv

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreManager.KEY_SP_NAME)
class DataStoreManager {


    companion object {
        const val KEY_SP_NAME = "SP"


        suspend fun <T> put(dataStore: DataStore<Preferences>, key:Preferences.Key<T>,value:T){
            dataStore.edit {preferences ->
                preferences[key] = value
            }
        }
        fun <T> getAsFlow(dataStore: DataStore<Preferences>, key:Preferences.Key<T>): Flow<T?> {
           val fv= dataStore.data.catch {e->
                throw  e
            }.filter {setting->
                setting.contains(key)
            }.map {
                it[key]
           }

            return fv
        }
        suspend fun <T> get(dataStore: DataStore<Preferences>,key:Preferences.Key<T>):T? {
            var v :T ?= null
            dataStore.edit { settings->
                v = settings[key]
            }
            return v
        }
    }
}