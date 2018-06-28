package com.agodatask.di.modules

import com.agodatask.api.NewsRequestsInterface
import com.agodatask.main.news.NewsLocalDataSource
import com.agodatask.main.news.NewsMvp
import com.agodatask.main.news.NewsPresenter
import com.agodatask.main.news.NewsRemoteDataSource
import com.agodatask.main.news.NewsInteractor
import com.agodatask.network.NetworkMonitorProvider
import com.agodatask.realm.RealmManager
import com.agodatask.sharedpref.SharedPreferencesProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by Sid on 27/06/2018.
 *
 * Injects all the required dependencies in the designated layers.
 */

@Module
class NewsModule(val view: NewsMvp.View) {

    @Provides
    fun providesApi(retrofit: Retrofit): NewsRequestsInterface {
        return retrofit.create(NewsRequestsInterface::class.java)
    }

    @Provides
    fun providesRemoteDataSource(api: NewsRequestsInterface): NewsMvp.RemoteDataSource {
        return NewsRemoteDataSource(api)
    }

    @Provides
    fun providesLocalDataSource(realmManger: RealmManager, pref: SharedPreferencesProvider): NewsMvp.LocalDataSource {
        return NewsLocalDataSource(realmManger, pref)
    }

    @Provides
    fun providesInteractor(remoteDataSource: NewsMvp.RemoteDataSource,
                           localDataSource: NewsMvp.LocalDataSource,
                           networkMonitorProvider: NetworkMonitorProvider): NewsMvp.Interactor {
        return NewsInteractor(remoteDataSource, localDataSource, networkMonitorProvider)
    }

    @Provides
    fun providesPresenter(interactor: NewsMvp.Interactor): NewsPresenter {
        return NewsPresenter(view, interactor)
    }
}