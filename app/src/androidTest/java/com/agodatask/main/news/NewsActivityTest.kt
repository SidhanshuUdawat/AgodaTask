package com.agodatask.main.news

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.agodatask.R
import com.agodatask.main.news.screens.NewsActivityScreen
import com.agodatask.util.RecyclerViewItemCountAssertion
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sid on 28/06/2018.
 */

@RunWith(AndroidJUnit4::class)
class NewsActivityTest {

    private val intent = Intent()

    @Rule
    @JvmField
    var activityTestRule = IntentsTestRule(NewsActivity::class.java, false, false)

    val screen = NewsActivityScreen()

    @Before
    fun setUp() {
        RESTMockServer.reset()
    }

    @Test
    fun it_hides_spinner_when_news_are_fetched() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")
        activityTestRule.launchActivity(intent)
        onView(withId((R.id.progressView))).check(matches(not(isDisplayed())))
    }

    @Test
    fun it_shows_error_screen_when_response_is_empty() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnEmpty(200)
        activityTestRule.launchActivity(intent)
        onView(withId((R.id.errorView))).check(matches(isDisplayed()))
    }

    @Test
    fun it_checks_for_news_item_count_when_response_is_fetched() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")
        activityTestRule.launchActivity(intent)
        Thread.sleep(100)
        onView(withId((R.id.newsRecyclerView))).check(RecyclerViewItemCountAssertion(5))
    }

    @Test
    fun given_error_screen_when_fetching_news_failed_then_retry_and_show_list_of_news() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnEmpty(200)

        activityTestRule.launchActivity(intent)
        onView(withId((R.id.errorView))).check(matches(isDisplayed()))

        RESTMockServer.reset()
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")
        onView(withId((R.id.errorRetryBtn))).perform(click())
        onView(withId((R.id.newsRecyclerView))).check(RecyclerViewItemCountAssertion(5))
    }

    @Test
    fun given_error_screen_when_fetching_news_failed_then_retry_and_show_error() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnEmpty(200)

        activityTestRule.launchActivity(intent)
        onView(withId((R.id.errorView))).check(matches(isDisplayed()))

        RESTMockServer.reset()
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnEmpty(200)
        onView(withId((R.id.errorRetryBtn))).perform(click())
        onView(withId((R.id.errorView))).check(matches(isDisplayed()))
    }

    @Test
    fun test_content_items_recyclerView() {
        RESTMockServer.whenGET(pathContains("/api/bins/nl6jh")).thenReturnFile(200, "api/news.json")
        activityTestRule.launchActivity(intent)
        Thread.sleep(100)
        screen {
            recycler {
                isVisible()
                hasSize(5)

                firstChild<NewsActivityScreen.Item> {
                    isVisible()
                    newsTitle { hasText("Work Policies May Be Kinder, but Brutal Competition Isn’t") }
                    newsByLine { hasText("By NOAM SCHEIBER") }
                }

                lastChild<NewsActivityScreen.Item> {
                    isVisible()
                    newsTitle { hasText("Data-Crunching Is Coming to Help Your Boss Manage Your Time") }
                    newsByLine { hasText("By DAVID STREITFELD") }
                }
            }
        }
    }
}