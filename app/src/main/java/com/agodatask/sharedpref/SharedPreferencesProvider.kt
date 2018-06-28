package com.agodatask.sharedpref

/**
 * Created by Sid on 26/06/2018.
 */

interface SharedPreferencesProvider {
    fun putBooleanData(key: String, value: Boolean)
    fun getBooleanData(key: String): Boolean
}