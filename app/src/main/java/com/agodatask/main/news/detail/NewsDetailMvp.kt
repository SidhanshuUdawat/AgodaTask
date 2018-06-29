package com.agodatask.main.news.detail

import com.agodatask.datasets.NewsEntity
import rx.Observable

/**
 * Created by Sid on 30/06/2018.
 */

interface NewsDetailMvp {

    interface View {
        fun setNewsTitle(title: String)
        fun setNewsImage(newsImageUrl: String)
        fun showNewsImage()
        fun hideNewsImage()
        fun setNewsDetails(detail: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Interactor {
        fun getNews(): Observable<NewsEntity>
    }
}