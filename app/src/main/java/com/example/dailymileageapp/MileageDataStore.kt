package com.example.dailymileageapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

// Define a top-level property for the DataStore instance
// The name "mileage_preferences" will be the filename for the DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mileage_preferences")

class MileageDataStore(private val context: Context) {

    // Define a Preferences Key for storing the mileage list as a JSON string
    companion object {
        val MILEAGE_LIST_KEY = stringPreferencesKey("mileage_list_json")
        val DAYS_OF_WEEK =
            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    }

    // Flow to emit the list of DailyMileage objects whenever it changes
    val dailyMileageFlow: Flow<List<DailyMileage>> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val jsonString = preferences[MILEAGE_LIST_KEY]
            if (jsonString != null) {
                try {
                    Json.decodeFromString<List<DailyMileage>>(jsonString)
                } catch (e: Exception) {
                    // Handle deserialization error, e.g., return default list
                    getDefaultMileageList()
                }
            } else {
                getDefaultMileageList()
            }
        }

    // Function to save the list of DailyMileage objects
    suspend fun saveMileageList(mileageList: List<DailyMileage>) {
        val jsonString = Json.encodeToString(mileageList)
        context.dataStore.edit { settings ->
            settings[MILEAGE_LIST_KEY] = jsonString
        }
    }

    // Function to clear all mileage data
    suspend fun clearMileageData() {
        context.dataStore.edit { settings ->
            settings.remove(MILEAGE_LIST_KEY)
            // Alternatively, to explicitly save an empty list:
            // val jsonString = Json.encodeToString(getDefaultMileageList())
            // settings[MILEAGE_LIST_KEY] = jsonString
        }
    }

    // Function to get the default list of days with empty mileage
    fun getDefaultMileageList(): List<DailyMileage> {
        return DAYS_OF_WEEK.map { DailyMileage(it, "") }
    }
}