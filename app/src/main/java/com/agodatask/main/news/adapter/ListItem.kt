package com.agodatask.main.news.adapter

/**
 * Created by Sid on 27/06/2018.
 */

interface ListItem {

    val itemType: ViewType

    enum class ViewType {
        NEWS,
        OTHER
    }
}