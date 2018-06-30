package com.agodatask.main.news.detail

import com.agodatask.R
import com.agodatask.datasets.NewsMultimedia
import com.agodatask.util.TestUtil
import com.agodatask.utils.MultimediaUtil
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by Sid on 30/06/2018.
 */

class NewsDetailPresenterTest {

    private lateinit var view: NewsDetailMvp.View
    private lateinit var interactor: NewsDetailMvp.Interactor
    private lateinit var presenter: NewsDetailPresenter


    @Before
    fun setUp() {
        view = mock(NewsDetailMvp.View::class.java)
        interactor = mock(NewsDetailMvp.Interactor::class.java)
        presenter = NewsDetailPresenter(view, interactor)
    }

    @Test
    fun it_gets_news_data_when_init() {
        `when`(interactor.getNews()).thenReturn(Observable.empty())
        presenter.init()
        verify(interactor).getNews()
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun it_sets_title_detail_and_fallback_image_when_init() {
        val newsEntity = TestUtil.createNews()

        presenter.onFetchingNewsSuccess(newsEntity)

        verify(view).setNewsTitle(newsEntity.title)
        verify(view).setNewsImage(R.drawable.place_holder)
        verify(view).hideProgress()
        verify(view).setNewsDetails(newsEntity.information)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun it_sets_title_information_and_image_when_init() {
        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, MultimediaUtil.IMAGE_FORMAT_THUMB_LARGE)
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, MultimediaUtil.IMAGE_FORMAT_MEDIUM_THREE)
        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)

        val newsEntity = TestUtil.createNews(multimediaList)

        presenter.onFetchingNewsSuccess(newsEntity)

        verify(view).setNewsTitle(newsEntity.title)
        verify(view).showNewsImage()
        verify(view).setNewsImage(imageURL2)
        verify(view).setNewsDetails(newsEntity.information)
        verifyNoMoreInteractions(view)
    }

}