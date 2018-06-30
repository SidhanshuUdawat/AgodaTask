package com.agodatask.main.news.detail

import android.content.Intent
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.agodatask.AgodaApplication
import com.agodatask.R
import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia
import com.agodatask.main.news.NewsActivity
import com.agodatask.main.news.screens.NewsDetailActivityScreen
import com.agodatask.model.RealmMultimedia
import com.agodatask.model.RealmNews
import com.agodatask.realm.RealmManager
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import io.realm.RealmList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sid on 30/06/2018.
 */

@RunWith(AndroidJUnit4::class)
class NewsDetailActivityTest {

    private val intent = Intent()

    @Rule
    @JvmField
    var newsActivityRule = IntentsTestRule(NewsActivity::class.java, false, false)

    @Rule
    @JvmField
    var newsDetailActivityRule = IntentsTestRule(NewsDetailActivity::class.java, false, false)

    private lateinit var realmManager: RealmManager
    private val context = getInstrumentation().targetContext.applicationContext

    val screen = NewsDetailActivityScreen()

    @Before
    fun setUp() {
        RESTMockServer.reset()
        //Delete all media from realm
        realmManager = (context as AgodaApplication).getApplicationComponent().getRealmManager()
        realmManager.clearData()
    }

    @Test
    fun it_shows_detail_screen_when_list_item_is_clicked() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")

        newsActivityRule.launchActivity(intent)
        Thread.sleep(100)
        onView(withId(R.id.newsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(hasClassName(NewsDetailActivity::class.java.name)))
    }

    @Test
    fun it_shows_detail_screen_with_news_details_on_the_screen() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")

        newsActivityRule.launchActivity(intent)
        Thread.sleep(100)
        onView(withId(R.id.newsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.newsTitleTxt)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsImage)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsInformationTxt)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsFullStoryBtn)).check(matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun it_shows_detail_screen_with_news_details_on_the_screen_read_from_realm() {
        val newsEntity = createNews()
        storeNews(newsEntity)

        val intent = NewsDetailActivity.getIntent(context, "some title")
        newsDetailActivityRule.launchActivity(intent)

        Thread.sleep(100)

        onView(withId(R.id.newsTitleTxt)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsImage)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsInformationTxt)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.newsFullStoryBtn)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.newsTitleTxt)).check(matches(withText("some title")))
        onView(withId(R.id.newsInformationTxt)).check(matches(withText("Some information")))

    }


    @Test
    fun it_checks_content_on_screen() {
        val newsEntity = createNews()
        storeNews(newsEntity)

        val intent = NewsDetailActivity.getIntent(context, "some title")
        newsDetailActivityRule.launchActivity(intent)

        screen {

            newsTitle {
                isVisible()
                withText("some title")
            }

            newsImage {
                isVisible()
            }

            newsInfoTxt {
                isVisible()
                withText("Some information")
            }

            fullStoryButton {
                isVisible()
                withText("Full Story")
            }
        }

    }

    private fun storeNews(newsEntity: NewsEntity) {
        val realmNewsToStore = createRealmNews(newsEntity)
        val realm = realmManager.realm
        try {
            realm.executeTransaction({
                val realmNews = realm.where(RealmNews::class.java).findFirst()
                realmNews.let {
                    realm.copyToRealmOrUpdate(realmNewsToStore)
                }
            })
        } finally {
            realmManager.closeRealm(realm)
        }
    }

    private fun createRealmNews(newsEntity: NewsEntity): RealmNews {

        val multimediList = if (newsEntity.multimedia != null && newsEntity!!.multimedia!!.isNotEmpty()) {
            val images = RealmList<RealmMultimedia>()
            for (image in newsEntity.multimedia!!) {
                images.add(RealmMultimedia(image.url))
            }
            images
        } else {
            RealmList()
        }
        return RealmNews(newsEntity.section, newsEntity.title,
                newsEntity.information, newsEntity.url,
                newsEntity.byline, newsEntity.item_type,
                newsEntity.material_type_facet, newsEntity.published_date, multimediList)
    }

    private fun createNews(multimedia: ArrayList<NewsMultimedia> = ArrayList()): NewsEntity {
        return NewsEntity("Some section", "some title",
                "Some information", "http://detail.com",
                "some by line", "Some item type",
                "some data", "10th July 2018",
                multimedia)
    }


}