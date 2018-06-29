package com.agodatask.main.news.detail

import com.agodatask.datasets.NewsEntity
import com.agodatask.main.news.NewsMvp
import rx.Observable

/**
 * Created by Sid on 30/06/2018.
 */

class NewsDetailInteractor(private val localDataSource: NewsMvp.LocalDataSource, private val newsTitle: String) : NewsDetailMvp.Interactor {

    override fun getNews(): Observable<NewsEntity> {
        return localDataSource.getNews(newsTitle)
    }

}