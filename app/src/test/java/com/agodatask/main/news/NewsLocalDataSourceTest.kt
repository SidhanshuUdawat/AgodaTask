package com.agodatask.main.news

import com.agodatask.realm.RealmMvp
import com.agodatask.sharedpref.SharedPreferencesProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by Sid on 28/06/2018.
 */

class NewsLocalDataSourceTest {

    private lateinit var realmManager: RealmMvp
    private lateinit var pref: SharedPreferencesProvider
    private lateinit var localDataSource: NewsMvp.LocalDataSource

    @Before
    fun setUp() {
        realmManager = mock(RealmMvp::class.java)
        pref = mock(SharedPreferencesProvider::class.java)
        localDataSource = NewsLocalDataSource(realmManager, pref)
    }

    @Test
    fun it_gets_boolean_data_from_preference_when_checking_for_local_data() {
        localDataSource.hasLocalData()
        verify(pref).getBooleanData(NewsLocalDataSource.IS_LOCAL_DATA_SAVED)
        verifyNoMoreInteractions(pref)
    }
}