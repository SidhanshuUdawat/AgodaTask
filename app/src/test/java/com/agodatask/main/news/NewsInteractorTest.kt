package com.agodatask.main.news

import com.agodatask.network.NetworkMonitorProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable

/**
 * Created by Sid on 28/06/2018.
 */
class NewsInteractorTest {

    private lateinit var remoteDataSource: NewsMvp.RemoteDataSource
    private lateinit var localDataSource: NewsMvp.LocalDataSource
    private lateinit var networkMonitorProvider: NetworkMonitorProvider
    private lateinit var interactor: NewsMvp.Interactor

    @Before
    fun setUp() {
        remoteDataSource = mock(NewsMvp.RemoteDataSource::class.java)
        localDataSource = mock(NewsMvp.LocalDataSource::class.java)
        networkMonitorProvider = mock(NetworkMonitorProvider::class.java)
        interactor = NewsInteractor(remoteDataSource, localDataSource, networkMonitorProvider)
    }


    @Test
    fun it_fetches_remote_news_when_network_is_connected() {
        `when`(networkMonitorProvider.isConnected()).thenReturn(true)
        `when`(remoteDataSource.getNews()).thenReturn(Observable.empty())

        interactor.getNews()
        verify(networkMonitorProvider).isConnected()
        verify(remoteDataSource).getNews()
        verifyNoMoreInteractions(networkMonitorProvider, remoteDataSource)
    }

    @Test
    fun it_fetches_local_news_when_network_is_not_connected_and_device_has_local_data() {
        `when`(networkMonitorProvider.isConnected()).thenReturn(false)
        `when`(localDataSource.hasLocalData()).thenReturn(true)
        `when`(localDataSource.getNews()).thenReturn(Observable.empty())

        interactor.getNews()
        verify(networkMonitorProvider).isConnected()
        verify(localDataSource).hasLocalData()
        verify(localDataSource).getNews()
        verifyNoMoreInteractions(networkMonitorProvider, localDataSource)
    }
}