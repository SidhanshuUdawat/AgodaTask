package com.agodatask.main.news.adapter

import com.agodatask.datasets.NewsEntity
import com.agodatask.main.news.NewsMvp
import com.agodatask.network.NetworkMonitorProvider
import rx.Observable

/**
 * Created by Sid on 27/06/2018.
 */

class NewsInteractor(private val remoteDataSource: NewsMvp.RemoteDataSource,
                     private val localDataSource: NewsMvp.LocalDataSource,
                     private val networkMonitorProvider: NetworkMonitorProvider) : NewsMvp.Interactor {

    override fun getNews(): Observable<List<NewsEntity>> {
        return when {
            networkMonitorProvider.isConnected() -> remoteDataSource.getNews()
                    .flatMap { newsModel ->
                        if (newsModel.results.isNotEmpty()) {
                            localDataSource.deleteLocalData()
                            storeNews(newsModel.results)
                            Observable.just(newsModel.results)
                        } else {
                            Observable.empty()
                        }
                    }
            localDataSource.hasLocalData() -> localDataSource.getNews()
            else -> Observable.just(null)
        }
    }

    private fun storeNews(newsList: List<NewsEntity>) {
        Observable.from(newsList)
                .doOnNext { news ->
                    localDataSource.storeNews(news)
                }.subscribe()
    }
}