package com.agodatask.main.news.adapter.news

import com.agodatask.R
import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia
import com.agodatask.util.TestUtil
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by Sid on 30/06/2018.
 */

class NewsViewHolderPresenterTest {

    private lateinit var view: NewsViewHolderMvp.View
    private lateinit var presenter: NewsViewHolderPresenter

    @Before
    fun setUp() {
        view = mock(NewsViewHolderMvp.View::class.java)
        presenter = NewsViewHolderPresenter(view)
    }


    @Test
    fun it_sets_title_information_and_fallback_image_when_init() {
        val newsEntity = TestUtil.createNews()
        val newsViewModel = createNewsViewModel(newsEntity)
        presenter.init(newsViewModel)

        verify(view).setNewsImage(R.drawable.place_holder)
        verify(view).hideProgress()
        verify(view).setByLine(newsEntity.byline!!)
        verify(view).setTitle(newsEntity.title)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_sets_title_information_and_image_when_init() {
        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, "thumbLarge")
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, "Standard Thumbnail")
        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)

        val newsEntity = TestUtil.createNews(multimediaList)
        val newsViewModel = createNewsViewModel(newsEntity)
        presenter.init(newsViewModel)

        verify(view).setNewsImage(imageURL1)
        verify(view).setByLine(newsEntity.byline!!)
        verify(view).setTitle(newsEntity.title)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_hides_progress_when_image_loading_success() {
        presenter.onImageLoadingSuccess()
        verify(view).hideProgress()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_hides_progress_when_image_loading_failed() {
        presenter.onImageLoadingFailed()
        verify(view).hideProgress()
        verifyNoMoreInteractions(view)
    }

    private fun createNewsViewModel(news: NewsEntity): NewsViewModel {
        return NewsViewModel(news)
    }
}