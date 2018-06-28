package com.agodatask.sharedpref

import android.content.SharedPreferences

/**
 * Created by Sid on 26/06/2018.
 *
 * Provides Shared Pref capabilities
 */

class MySharedPreferences(private val sharedPreferences: SharedPreferences) : SharedPreferencesProvider {

    /**
     * Inserting value of type boolean
     */
    override fun putBooleanData(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Reading value of type boolean
     */
    override fun getBooleanData(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

}
