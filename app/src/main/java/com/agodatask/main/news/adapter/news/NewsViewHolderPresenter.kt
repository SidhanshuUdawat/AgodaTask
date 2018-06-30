package com.agodatask.main.news.adapter.news

import com.agodatask.R
import com.agodatask.datasets.NewsEntity
import com.agodatask.utils.MultimediaUtil

/**
 * Created by Sid on 27/06/2018.
 */
class NewsViewHolderPresenter(val view: NewsViewHolderMvp.View) {

    lateinit var newsEntity: NewsEntity

    fun init(newsViewModel: NewsViewModel) {
        newsEntity = newsViewModel.newsEntity

        val newsImageUrl = MultimediaUtil.getNewsImageUrl(newsEntity.multimedia, MultimediaUtil.IMAGE_FORMAT_THUMB_LARGE)
        if (newsImageUrl.isNotEmpty()) {
            view.setNewsImage(newsImageUrl)
        } else {
            showFallBackImage()
        }
        view.setTitle(newsEntity.title)
        newsEntity.byline?.let { byLine ->
            view.setByLine(byLine)
        }
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

    fun onContainerClicked() {
        newsEntity.let { news ->
            view.onContainerClicked(news.title)
        }
    }
}