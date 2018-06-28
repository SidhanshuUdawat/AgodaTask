package com.agodatask.main.news.screens

import android.view.View
import com.agoda.kakao.KRecyclerView
import com.agoda.kakao.KRecyclerItem
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import com.agodatask.R
import org.hamcrest.Matcher

/**
 * Created by Sid on 28/06/2018.
 */

open class TestRecyclerScreen : Screen<TestRecyclerScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.newsRecyclerView)
    }, itemTypeBuilder = {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val newsTitle: KTextView = KTextView(parent) { withId(R.id.newsTitle) }
        val newsByLine: KTextView = KTextView(parent) { withId(R.id.newsByLine) }
    }
}