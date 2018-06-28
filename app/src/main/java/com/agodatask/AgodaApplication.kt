package com.agodatask

import android.app.Application
import com.agodatask.di.components.DaggerApplicationComponent
import com.agodatask.di.modules.ApiModule
import com.agodatask.di.modules.ApplicationModule
import com.agodatask.di.modules.RealmManagerModule
import com.agodatask.di.modules.SharedPreferencesModule
import com.agodatask.di.provider.ApplicationBaseComponent

/**
 * Created by Sid on 25/06/2018.
 * 1. Application Module provides context to underlying dependencies
 * 2. Api module - Provides retrofit instance
 * 3. SharedPref module - Provides SharedPreferences
 * 4. Realm module - Local storage (Replacement of SQLite)
 */


open class AgodaApplication : Application() {
    lateinit var appProvider: ApplicationBaseComponent

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
        setupRealm()
    }

    /**
     * Creates application component and inject all the required dependencies
     */
    open fun createAppComponent() {
        appProvider = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule("https://api.myjson.com/"))
                .sharedPreferencesModule(SharedPreferencesModule())
                .realmManagerModule(RealmManagerModule())
                .build()
    }

    /**
     * Initialising Realm
     */
    private fun setupRealm() {
        getApplicationComponent().getRealmManager().init()
    }

    /**
     * Application component provider
     */
    fun getApplicationComponent(): ApplicationBaseComponent {
        return appProvider
    }
}