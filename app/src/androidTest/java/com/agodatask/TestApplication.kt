package com.agodatask

import com.agodatask.di.components.DaggerApplicationComponent
import com.agodatask.di.modules.ApiModule
import com.agodatask.di.modules.ApplicationModule
import com.agodatask.di.modules.RealmManagerModule
import com.agodatask.di.modules.SharedPreferencesModule
import io.appflate.restmock.RESTMockServer

/**
 * Created by Sid on 28/06/2018.
 */

class TestApplication : AgodaApplication() {

    override fun createAppComponent() {
        val baseUrl = RESTMockServer.getUrl() + "api/"
        appProvider = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule(baseUrl))
                .sharedPreferencesModule(SharedPreferencesModule())
                .realmManagerModule(RealmManagerModule())
                .build()
    }
}