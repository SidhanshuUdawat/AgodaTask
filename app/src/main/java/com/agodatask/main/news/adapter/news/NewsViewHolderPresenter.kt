package com.agodatask.main.news.adapter.news

import com.agodatask.R
import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia

/**
 * Created by Sid on 27/06/2018.
 */
class NewsViewHolderPresenter(val view: NewsViewHolderMvp.View) {

    companion object {
        const val MAX_IMAGE_DIMEN = "640"
    }

    lateinit var newsEntity: NewsEntity

    fun init(newsViewModel: NewsViewModel) {
        newsEntity = newsViewModel.newsEntity
    }

    /**
     * Api provides list of images to display and their order is not always correct
     * in order to display the most optimum image to display list is filter using dimension as 640
     */
    fun getNewsImageUrl(newsEntity: NewsEntity): String {
        return if (newsEntity.multimedia != null && newsEntity.multimedia.isNotEmpty()) {
            val imageURL = newsEntity.multimedia.filter { image -> image.url.contains(MAX_IMAGE_DIMEN) }
            if (imageURL.isNotEmpty()) {
                imageURL[0].url
            } else {
                newsEntity.multimedia[0].url
            }
        } else {
            ""
        }
    }

    private fun showFallBackImage() {
        view.setNewsImage(R.drawable.no_image_available)
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