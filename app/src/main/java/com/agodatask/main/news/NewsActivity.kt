package com.agodatask.main.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.agodatask.AgodaApplication
import com.agodatask.R
import com.agodatask.di.components.DaggerNewsComponent
import com.agodatask.di.modules.NewsModule
import com.agodatask.main.news.adapter.ListItem
import com.agodatask.main.news.adapter.NewsListAdapter
import com.agodatask.main.news.adapter.RecyclerViewDecorator
import com.agodatask.main.news.detail.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), NewsMvp.View {

    private lateinit var newsList: MutableList<ListItem>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var newsListAdapter: NewsListAdapter

    @Inject
    lateinit var presenter: NewsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setErrorRetryClickListener()
        setupNewsList()
        injectDependencies()
    }

    /**
     * Application component is injected along with news module.
     */
    private fun injectDependencies() {
        val applicationComponent = (applicationContext as AgodaApplication).getApplicationComponent()
        DaggerNewsComponent.builder()
                .applicationBaseComponent(applicationComponent)
                .newsModule(NewsModule(this))
                .build().inject(this)
        presenter.init()
    }

    /**
     * Initial setup of empty news list
     */
    private fun setupNewsList() {
        newsList = ArrayList()

        val listener = object : NewsListAdapter.OnListInteraction {
            override fun onNewsClicked(newsTitle: String) {
                presenter.onNewsClicked(newsTitle)
            }
        }

        newsListAdapter = NewsListAdapter(newsList, listener)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val upMargin = resources.getDimensionPixelSize(R.dimen.news_list_separator)
        newsRecyclerView.addItemDecoration(RecyclerViewDecorator(upMargin))
        newsRecyclerView.layoutManager = layoutManager
        newsRecyclerView.itemAnimator = DefaultItemAnimator()
        newsRecyclerView.adapter = newsListAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setErrorRetryClickListener() {
        errorRetryBtn.setOnClickListener {
            presenter.onRetryClicked()
        }
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressView.visibility = View.GONE
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
    }

    override fun hideError() {
        errorView.visibility = View.GONE
    }

    override fun resetList() {
        val previousSize = newsList.size
        newsList.clear()
        newsListAdapter.notifyItemRangeRemoved(0, previousSize)
    }

    override fun updateList(list: List<ListItem>) {
        val previousSize = newsList.size
        newsList.addAll(previousSize, list)
        newsListAdapter.notifyItemRangeInserted(previousSize, list.size)
    }

    override fun addItemToList(item: ListItem) {
        val oldSize = newsList.size
        newsList.add(item)
        newsListAdapter.notifyItemInserted(oldSize)
    }

    override fun removeItemFromList(item: ListItem) {
        val oldSize = newsList.size
        newsList.remove(item)
        newsListAdapter.notifyItemRemoved(oldSize)
    }

    override fun showMoreDetails(newsTitle: String) {
        val intent = NewsDetailActivity.getIntent(this, newsTitle)
        startActivity(intent)
    }
}