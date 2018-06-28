package com.agodatask.main.news

import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsModel
import com.agodatask.main.news.adapter.ListItem
import rx.Observable

/**
 * Created by Sid on 27/06/2018.
 */
class NewsMvp {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError()
        fun hideError()
        fun resetList()
        fun updateList(list: List<ListItem>)
        fun addItemToList(item: ListItem)
        fun removeItemFromList(item: ListItem)
        fun showMoreDetails(newsTitle: String)
    }

    interface Interactor {
        fun getNews(): Observable<List<NewsEntity>>
    }

    interface RemoteDataSource {
        fun getNews(): Observable<NewsModel>
    }

    interface LocalDataSource {
        fun hasLocalData(): Boolean
        fun deleteLocalData()
        fun storeNews(news: List<NewsEntity>)
        fun getNews(): Observable<List<NewsEntity>>
        fun getNews(newsTitle: String): Observable<NewsEntity>
    }

}