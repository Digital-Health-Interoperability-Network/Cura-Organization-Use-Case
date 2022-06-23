package com.nameksolutions.regchain.curaorganization.utils

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {

    //get the application context to be used whenever this class is called
    private val applicationContext = context.applicationContext

    //the dataStore component is a modification of the shared prefs method which saves data in key-value pair
    private val dataStore: DataStore<Preferences>

//    private val dataStore = context.dataStore

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )//.createDataStore(

        //)
    }

    //function to fetch the stored auth token and put into a map with the key
    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    //function to fetch the stored value of of the app is being used for the 1st time
    val isFirstTime: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_IS_FIRST_TIME]
        }

    //function to fetch the stored organisation name and put into a map with the key
    val organisationName: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_ORGANISATION_NAME]
        }

    //function to save the user's token using the already defined key
    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preference ->
            preference[KEY_AUTH] = authToken
        }
    }

    //function to save the value of of the app is being used for the 1st time
    suspend fun saveIsFirstTime(isFirstTime: Boolean = true){
        dataStore.edit { preference ->
            preference[KEY_IS_FIRST_TIME] = isFirstTime
        }
    }

    //function to save the organisation's name using the already defined key
    suspend fun saveOrganisationName(organisationName: String) {
        dataStore.edit { preference ->
            preference[KEY_ORGANISATION_NAME] = organisationName
        }
    }


    //?should the log out clear the db or just the stored token?
    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        //        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val KEY_IS_FIRST_TIME = preferencesKey<Boolean>("key_is_first_time")
        private val KEY_ORGANISATION_NAME = preferencesKey<String>("key_organisation_name")
//        private val KEY_EMAIL = preferencesKey<String>("key_email")
//        private val KEY_GENDER = preferencesKey<String>("key_gender")
//        private val KEY_GIVEN_NAME = preferencesKey<String>("key_given_name")
//        private val KEY_FAMILY_NAME = preferencesKey<String>("key_family_name")
//        private val KEY_PASSWORD = preferencesKey<String>("key_password")
//        private val KEY_TELECOM = preferencesKey<String>("key_telecom")
    }

}