package com.agodatask.main.news.screens

import com.agoda.kakao.KButton
import com.agoda.kakao.KView
import com.agoda.kakao.Screen
import com.agodatask.R

/**
 * Created by Sid on 28/06/2018.
 */

open class NewsDetailActivityScreen : Screen<NewsDetailActivityScreen>() {
    val newsTitle: KView = KView { withId(R.id.newsTitleTxt) }
    val newsImage: KView = KView { withId(R.id.newsImage) }
    val newsInfoTxt: KView = KView { withId(R.id.newsInformationTxt) }
    val fullStoryButton: KButton = KButton { withId(R.id.newsFullStoryBtn) }
}