package com.agodatask.di.provider


import android.app.Application
import android.content.Context
import com.agodatask.network.NetworkMonitorProvider
import com.agodatask.realm.RealmManager
import com.agodatask.sharedpref.SharedPreferencesProvider
import retrofit2.Retrofit

/**
 * Created by Sid on 26/06/2018.
 */
interface ApplicationBaseComponent {

    fun application(): Application

    fun getContext(): Context

    fun getSharedPrefProvider(): SharedPreferencesProvider

    fun getRetrofit(): Retrofit

    fun getRealmManager(): RealmManager

    fun getNetworkMonitor(): NetworkMonitorProvider
}