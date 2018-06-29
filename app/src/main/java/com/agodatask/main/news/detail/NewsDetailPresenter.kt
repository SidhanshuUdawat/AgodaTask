package com.agodatask.main.news.detail

import com.agodatask.datasets.NewsEntity
import com.agodatask.utils.MultimediaUtil
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by Sid on 30/06/2018.
 */
class NewsDetailPresenter(private val view: NewsDetailMvp.View, private val interactor: NewsDetailMvp.Interactor) {

    companion object {
        const val IMAGE_FORMAT = "thumbLarge"
    }

    private val compositeSubscription = CompositeSubscription()

    fun init() {
        getNewsData()
    }

    private fun getNewsData() {
        compositeSubscription.add(interactor.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<NewsEntity> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(newsEntity: NewsEntity) {
                        onFetchingNewsSuccess(newsEntity)
                    }
                }))
    }

    fun onFetchingNewsSuccess(newsEntity: NewsEntity) {
        view.setNewsTitle(newsEntity.title)

        val newsImageUrl = MultimediaUtil.getNewsImageUrl(newsEntity.multimedia, IMAGE_FORMAT)
        if (newsImageUrl.isNotEmpty()) {
            view.showNewsImage()
            view.setNewsImage(newsImageUrl)
        } else {
            view.hideNewsImage()
        }
        view.setNewsDetails(newsEntity.information)
    }
}