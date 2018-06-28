package com.agodatask.realm

import android.content.Context
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Sid on 26/06/2018.
 *
 * A wrapper around Realm
 */

class RealmManager(private val context: Context) : RealmMvp {

    lateinit var realmConfiguration: RealmConfiguration

    companion object {
        private const val REALM_SCHEMA_VERSION = 1L
    }

    /**
     * Setting Realm library up.
     * deleteRealmIfMigrationNeeded is used to avoid the app from crashing when the schema of
     * the database changes from one version to another.
     */
    override fun init() {
        try {
            Realm.init(context)
            realmConfiguration = RealmConfiguration.Builder()
                    .schemaVersion(REALM_SCHEMA_VERSION)
                    .deleteRealmIfMigrationNeeded()
                    .build()
            Realm.setDefaultConfiguration(realmConfiguration)
        } catch (e: UnsatisfiedLinkError) {
            val error = "Something wrong with realm"
            Log.e("Realm", error)
        }

    }

    /**
     * Returns a Realm instance
     */
    override val realm: Realm
        get() = Realm.getDefaultInstance()

    /**
     * Closes the Realm
     */
    override fun closeRealm(realm: Realm?) {
        try {
            if (realm != null && !realm.isClosed) {
                realm.close()
            }
        } catch (e: Exception) {
            Log.e("Realm", "Closing Error: " + e.message)
        }

    }

    /**
     * Clears the Realm
     */
    override fun clearData() {
        try {
            Realm.deleteRealm(realmConfiguration)
        } catch (ex: Exception) {
            Log.e("Realm", "Clearing Error: " + ex.message)
        }

    }
}