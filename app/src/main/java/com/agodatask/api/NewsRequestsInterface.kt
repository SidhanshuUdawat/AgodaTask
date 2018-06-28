package com.agodatask.api

import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsModel
import retrofit2.http.GET
import rx.Observable

/**
 * Created by Sid on 26/06/2018.
 */

interface NewsRequestsInterface {

    @GET("bins/nl6jh")
    fun getNewsList(): Observable<NewsModel>

}