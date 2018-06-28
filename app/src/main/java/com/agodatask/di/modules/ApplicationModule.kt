package com.agodatask.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sid on 26/06/2018.
 *
 * Provides singleton instance of Application and Application Context
 */

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return app.applicationContext
    }
}