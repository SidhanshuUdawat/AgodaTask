package com.agodatask.main.news.detail

import com.agodatask.main.news.NewsMvp
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by Sid on 30/06/2018.
 */
class NewsDetailInteractorTest {

    private lateinit var localDataSource: NewsMvp.LocalDataSource
    private lateinit var interactor: NewsDetailMvp.Interactor
    private val newsTitle = "Some News Title"

    @Before
    fun setUp() {
        localDataSource = mock(NewsMvp.LocalDataSource::class.java)
        interactor = NewsDetailInteractor(localDataSource, newsTitle)
    }

    @Test
    fun it_gets_news_from_local_data_source() {
        `when`(interactor.getNews()).thenReturn(Observable.empty())

        interactor.getNews()
        verify(localDataSource).getNews(newsTitle)
        verifyNoMoreInteractions(localDataSource)
    }

}