package com.agodatask.main.news

import com.agodatask.datasets.NewsEntity
import com.agodatask.util.TestUtil
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by Sid on 30/06/2018.
 */

class NewsPresenterTest {

    private lateinit var view: NewsMvp.View
    private lateinit var interactor: NewsMvp.Interactor
    private lateinit var presenter: NewsPresenter

    @Before
    fun setUp() {
        view = mock(NewsMvp.View::class.java)
        interactor = mock(NewsMvp.Interactor::class.java)
        presenter = NewsPresenter(view, interactor)
    }

    @Test
    fun it_fetches_news_list_when_init() {
        `when`(interactor.getNews()).thenReturn(Observable.empty())

        presenter.init()
        verify(view).showProgress()
        verify(interactor).getNews()
    }

    @Test
    fun it_shows_error_when_fetching_news_failed() {
        presenter.onFetchingNewsFailed()

        verify(view).hideProgress()
        verify(view).showError()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_shows_error_when_api_returned_empty_list_of_news() {
        val emptyList = ArrayList<NewsEntity>()
        presenter.onFetchingNewsSuccess(emptyList)

        verify(view).hideProgress()
        verify(view).showError()
        verifyNoMoreInteractions(view)
    }


    @Test
    fun it_updates_list_with_fetched_news_list() {
        val newsEntity = TestUtil.createNews()
        val newsList = ArrayList<NewsEntity>()
        newsList.add(newsEntity)
        presenter.onFetchingNewsSuccess(newsList)

        verify(view).hideProgress()
        verify(view).updateList(ArgumentMatchers.anyList())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_shows_progress_hide_error_and_fetches_news_when_retry() {
        `when`(interactor.getNews()).thenReturn(Observable.empty())

        presenter.onRetryClicked()
        verify(view).showProgress()
        verify(view).hideError()
        verify(interactor).getNews()
    }

}