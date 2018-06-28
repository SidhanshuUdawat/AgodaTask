package com.agodatask.main.news.adapter.news

import com.agodatask.datasets.NewsEntity
import com.agodatask.main.news.adapter.ListItem

/**
 * Created by Sid on 27/06/2018.
 */

data class NewsViewModel(val newsEntity: NewsEntity) : ListItem {
    override val itemType: ListItem.ViewType
        get() = ListItem.ViewType.NEWS
}