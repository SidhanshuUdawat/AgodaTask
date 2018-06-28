package com.agodatask.main.news

import com.agodatask.api.NewsRequestsInterface
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by Sid on 28/06/2018.
 */
class NewsRemoteDataSourceTest {

    private lateinit var api: NewsRequestsInterface
    private lateinit var remoteDataSource: NewsMvp.RemoteDataSource

    @Before
    fun setUp() {
        api = mock(NewsRequestsInterface::class.java)
        remoteDataSource = NewsRemoteDataSource(api)
    }

    @Test
    fun it_gets_news() {
        `when`(api.getNewsList()).thenReturn(Observable.empty())

        remoteDataSource.getNews()
        verify(api).getNewsList()
        verifyNoMoreInteractions(api)
    }
}