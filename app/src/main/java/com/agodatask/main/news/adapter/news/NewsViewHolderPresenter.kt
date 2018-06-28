package com.agodatask.main.news.adapter.news

import com.agodatask.R
import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia

/**
 * Created by Sid on 27/06/2018.
 */
class NewsViewHolderPresenter(val view: NewsViewHolderMvp.View) {

    companion object {
        const val IMAGE_FORMAT = "thumbLarge"

    }

    lateinit var newsEntity: NewsEntity

    fun init(newsViewModel: NewsViewModel) {
        newsEntity = newsViewModel.newsEntity

        val newsImageUrl = getNewsImageUrl(newsEntity)
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

    /**
     * Api provides list of images to display and their may not always be correct
     * in order to display the most optimum image to display list is filter using dimension as 640
     */
    fun getNewsImageUrl(newsEntity: NewsEntity): String {
        return if (newsEntity.multimedia != null && newsEntity.multimedia.isNotEmpty()) {
            val imageURL = newsEntity.multimedia.filter { image -> image.format.toLowerCase().contentEquals(IMAGE_FORMAT.toLowerCase()) }
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