package com.agodatask.di.modules

import com.agodatask.main.news.NewsLocalDataSource
import com.agodatask.main.news.NewsMvp
import com.agodatask.main.news.detail.NewsDetailInteractor
import com.agodatask.main.news.detail.NewsDetailMvp
import com.agodatask.main.news.detail.NewsDetailPresenter
import com.agodatask.realm.RealmManager
import com.agodatask.sharedpref.SharedPreferencesProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Sid on 30/06/2018.
 */

@Module
class NewsDetailModule(private val view: NewsDetailMvp.View, private val newsTitle: String) {

    @Provides
    fun providesLocalDataSource(realmManger: RealmManager, pref: SharedPreferencesProvider): NewsMvp.LocalDataSource {
        return NewsLocalDataSource(realmManger, pref)
    }

    @Provides
    fun providesInteractor(localDataSource: NewsMvp.LocalDataSource): NewsDetailMvp.Interactor {
        return NewsDetailInteractor(localDataSource, newsTitle)
    }

    @Provides
    fun providesPresenter(interactor: NewsDetailMvp.Interactor): NewsDetailPresenter {
        return NewsDetailPresenter(view, interactor)
    }
}