package com.agodatask.main.news.adapter.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.agodatask.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.list_news.view.*

/**
 * Created by Sid on 27/06/2018.
 */
class NewsViewHolder(itemView: View, private val listener: OnNewsViewHolderInteraction) : RecyclerView.ViewHolder(itemView), NewsViewHolderMvp.View {

    interface OnNewsViewHolderInteraction {
        fun onNewsClicked(newsTitle: String)
    }

    private val presenter = NewsViewHolderPresenter(this)

    fun init(newsViewModel: NewsViewModel) {
        presenter.init(newsViewModel)
        itemView.setOnClickListener { presenter.onContainerClicked() }
    }

    override fun setTitle(title: String) {
        itemView.newsName.text = title
    }

    override fun setPrice(price: String) {
        itemView.newsPrice.text = price
    }

    override fun setFuelType(fuelType: String) {
        itemView.newsFuelType.text = fuelType
    }

    override fun setNewsImage(newsImageUrl: String) {
        val imageCorner = itemView.context.resources.getDimension(R.dimen.rounded_image_corners).toInt()
        val transformation = RoundedCornersTransformation(imageCorner, 0, RoundedCornersTransformation.CornerType.LEFT)
        Picasso.get()
                .load(newsImageUrl)
                .transform(transformation)
                .into(itemView.newsImage, object : Callback {
                    override fun onSuccess() {
                        presenter.onImageLoadingSuccess()
                    }

                    override fun onError(e: Exception?) {
                        presenter.onImageLoadingFailed()
                    }

                })
    }

    override fun setNewsImage(newsImageId: Int) {
        val imageCorner = itemView.context.resources.getDimension(R.dimen.rounded_image_corners).toInt()
        val transformation = RoundedCornersTransformation(imageCorner, 0, RoundedCornersTransformation.CornerType.LEFT)
        Picasso.get()
                .load(newsImageId)
                .transform(transformation)
                .into(itemView.newsImage)
    }

    override fun showProgress() {
        itemView.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        itemView.progressBar.visibility = View.GONE
    }

    override fun onContainerClicked(newsTitle: String) {
        listener.onNewsClicked(newsTitle)
    }
}