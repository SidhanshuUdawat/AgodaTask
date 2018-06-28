package com.agodatask.di.modules

import android.content.Context
import com.agodatask.network.LiveNetworkMonitor
import com.agodatask.network.NetworkMonitorProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sid on 26/06/2018.
 *
 * Provides single instance of Network monitoring class
 */

@Module
class NetworkMonitorModule {

    @Provides
    @Singleton
    internal fun provideNetworkMonitorProvider(context: Context): NetworkMonitorProvider {
        return LiveNetworkMonitor(context)
    }
}