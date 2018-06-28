package com.agodatask.main.news

import com.agodatask.datasets.NewsEntity
import com.agodatask.main.news.adapter.ListItem
import com.agodatask.main.news.adapter.news.NewsViewModel
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by Sid on 27/06/2018.
 *
 * Fetches the news data and processes it and passes to the View of displaying purpose.
 */


class NewsPresenter(private val view: NewsMvp.View, private val interactor: NewsMvp.Interactor) {

    private val compositeSubscription = CompositeSubscription()


    /**
     * Initial setup
     */
    fun init() {
        getNews()
    }

    /**
     * This asks the Interactor for the News data.
     * Presenter doesn't care from where the data is coming from may it be remote or local.
     */
    private fun getNews() {
        view.showProgress()
        compositeSubscription.add(interactor.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<NewsEntity>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        onFetchingNewsFailed()
                    }

                    override fun onNext(newsList: List<NewsEntity>) {
                        onFetchingNewsSuccess(newsList)
                    }
                }))
    }

    /**
     * This will be called in 2 scenarios
     * 1. App starts for the first time and there is no internet connection and no local data.
     * 2. Or API sends data which is not suppose to be null.
     */
    fun onFetchingNewsFailed() {
        view.hideProgress()
        view.showError()
    }

    fun onFetchingNewsSuccess(newsList: List<NewsEntity>) {
        view.hideProgress()
        if (newsList.isEmpty()) {
            view.showError()
        } else {
            processNews(newsList)
        }
    }

    /**
     * This converts the API News data model to News view model.
     */
    private fun processNews(newsList: List<NewsEntity>) {
        val viewModelList = ArrayList<ListItem>()
        for (i in newsList.indices) {
            val news = newsList[i]
            val newsViewModel = NewsViewModel(news)
            viewModelList.add(newsViewModel)
        }
        view.updateList(viewModelList)
    }

    fun onRetryClicked() {
        view.hideError()
        getNews()
    }

    fun onNewsClicked(newsTitle: String) {
        view.showMoreDetails(newsTitle)
    }

    fun onDestroy() {
        compositeSubscription.clear()
    }

}