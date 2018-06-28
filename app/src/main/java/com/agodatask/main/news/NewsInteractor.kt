package com.agodatask.main.news

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
                    .doOnNext {
                        localDataSource.deleteLocalData()
                        localDataSource.storeNews(it.results)
                    }
                    .flatMap {
                        Observable.just(it.results)
                    }
            localDataSource.hasLocalData() -> localDataSource.getNews()
            else -> Observable.just(null)
        }
    }
}