package com.agodatask.main.news.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.agodatask.AgodaApplication
import com.agodatask.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject

class NewsDetailActivity : AppCompatActivity(), NewsDetailMvp.View {

    @Inject
    lateinit var presenter: NewsDetailPresenter

    companion object {
        private const val NEWS_TITLE = "news_title"

        @JvmStatic
        fun getIntent(context: Context, newsTitle: String): Intent {
            var mIntent = Intent(context, NewsDetailActivity::class.java)
            mIntent.putExtra(NEWS_TITLE, newsTitle)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
    }

    private fun injectDependencies() {
        val vehicleId = intent.getIntExtra(NEWS_TITLE, 0)
        val applicationComponent = (applicationContext as AgodaApplication).getApplicationComponent()

        presenter.init()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun setNewsTitle(title: String) {
        newsTitleTxt.text = title
    }

    override fun setNewsImage(newsImageUrl: String) {
        Picasso.get()
                .load(newsImageUrl)
                .into(newsImage, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {

                    }

                })
    }

    override fun showNewsImage() {
        newsImage.visibility = View.VISIBLE
    }

    override fun hideNewsImage() {
        newsImage.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setNewsDetails(detail: String) {

    }
}
