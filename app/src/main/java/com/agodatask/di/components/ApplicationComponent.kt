package com.agodatask.di.components

import com.agodatask.di.modules.*
import com.agodatask.di.provider.ApplicationBaseComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Sid on 26/06/2018.
 */

@Singleton
@Component(modules = [ApplicationModule::class, SharedPreferencesModule::class,
    ApiModule::class, RealmManagerModule::class, NetworkMonitorModule::class])
interface ApplicationComponent : ApplicationBaseComponent