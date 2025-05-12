package com.site7x24learn.internshipfrontend.data.datasources.local

import android.content.Context

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
// data/datasources/local/
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences(
        "auth_prefs",
        Context.MODE_PRIVATE
    )

    fun saveAuthToken(token: String) {
        sharedPrefs.edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPrefs.getString("auth_token", null)
    }

    fun clearToken() {
        sharedPrefs.edit().remove("auth_token").apply()
    }
//    fun getUserId(): Int {
//        return sharedPrefs.getInt("user_id", -1)
//    }
}