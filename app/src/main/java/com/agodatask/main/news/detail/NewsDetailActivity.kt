package com.agodatask.main.news.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.agodatask.AgodaApplication
import com.agodatask.R
import com.agodatask.di.components.DaggerNewsDetailComponent
import com.agodatask.di.modules.NewsDetailModule
import com.agodatask.main.news.webview.WebViewActivity
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
        initToolbar()
        setFullStoryClickListener()
        injectDependencies()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setFullStoryClickListener() {
        newsFullStoryBtn.setOnClickListener {
            presenter.onNewsFullStoryClicked()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun injectDependencies() {
        val newsTitle = intent.getStringExtra(NEWS_TITLE)
        val applicationComponent = (applicationContext as AgodaApplication).getApplicationComponent()

        DaggerNewsDetailComponent.builder()
                .applicationBaseComponent(applicationComponent)
                .newsDetailModule(NewsDetailModule(this, newsTitle))
                .build().inject(this)

        presenter.init()
    }

    override fun setNewsTitle(title: String) {
        newsTitleTxt.text = title
    }

    override fun setNewsImage(newsImageUrl: String) {
        Picasso.get()
                .load(newsImageUrl)
                .into(newsImage, object : Callback {
                    override fun onSuccess() {
                        presenter.onImageLoadingSuccess()
                    }

                    override fun onError(e: Exception?) {
                        presenter.onImageLoadingFailed()
                    }

                })
    }

    override fun setNewsImage(newsImageId: Int) {
        Picasso.get()
                .load(newsImageId)
                .into(newsImage)
    }

    override fun setNewsDetails(detail: String) {
        newsInformationTxt.text = detail
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

    override fun showFullStory(url: String) {
        val intent = WebViewActivity.getIntent(this, url)
        startActivity(intent)
    }

    override fun showError(errorId: Int) {
        Toast.makeText(this, getString(errorId), Toast.LENGTH_SHORT)
    }
}
