package com.agodatask.di.modules

import android.content.Context
import com.agodatask.sharedpref.MySharedPreferences
import com.agodatask.sharedpref.SharedPreferencesProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sid on 26/06/2018.
 *
 * Provides single instance of SharedPreferences
 */

@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    internal fun provideSharedPreferencesProvider(context: Context): SharedPreferencesProvider {
        val preName = "AgodaPref"
        return MySharedPreferences(context.getSharedPreferences(preName, Context.MODE_PRIVATE))
    }
}