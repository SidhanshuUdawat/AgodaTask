package com.agodatask.main.news

import com.agodatask.api.NewsRequestsInterface

/**
 * Created by Sid on 27/06/2018.
 */

class NewsRemoteDataSource(private val api: NewsRequestsInterface) : NewsMvp.RemoteDataSource {
    override fun getNews() = api.getNewsList()
}