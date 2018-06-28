package com.agodatask.main.news.adapter.news

/**
 * Created by Sid on 27/06/2018.
 */
class NewsViewHolderMvp {
    interface View {
        fun setTitle(title: String)
        fun setPrice(price: String)
        fun setFuelType(fuelType: String)
        fun setNewsImage(newsImageUrl: String)
        fun setNewsImage(fallbackImageId: Int)
        fun showProgress()
        fun hideProgress()
        fun onContainerClicked(newTitle: String)
    }
}