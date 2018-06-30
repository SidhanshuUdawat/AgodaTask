package com.agodatask.main.news.detail

import com.agodatask.R
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

        val newsImageUrl = MultimediaUtil.getNewsImageUrl(newsEntity.multimedia, MultimediaUtil.IMAGE_FORMAT_MEDIUM_THREE)
        if (newsImageUrl.isNotEmpty()) {
            view.showNewsImage()
            view.setNewsImage(newsImageUrl)
        } else {
            showFallBackImage()
        }
        view.setNewsDetails(newsEntity.information)
    }

    private fun showFallBackImage() {
        view.setNewsImage(R.drawable.place_holder)
        view.hideProgress()
    }

    fun onImageLoadingSuccess() {
        view.hideProgress()
    }

    fun onImageLoadingFailed() {
        view.hideProgress()
    }
}