package com.agodatask.main.news

import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia
import com.agodatask.model.RealmMultimedia
import com.agodatask.model.RealmNews
import com.agodatask.realm.RealmMvp
import com.agodatask.sharedpref.SharedPreferencesProvider
import io.realm.RealmList
import rx.Observable

/**
 * Created by Sid on 27/06/2018.
 */

class NewsLocalDataSource(private val realmManager: RealmMvp, private val pref: SharedPreferencesProvider) : NewsMvp.LocalDataSource {

    companion object {
        const val IS_LOCAL_DATA_SAVED = "is_local_data_saved"
    }

    /**
     * It checks with the shared pref is local data is present or not
     */
    override fun hasLocalData(): Boolean {
        return pref.getBooleanData(IS_LOCAL_DATA_SAVED)
    }

    /**
     * Deletes all the locally stored news
     */
    override fun deleteLocalData() {
        val realm = realmManager.realm
        try {
            realm.executeTransaction({
                val realmNews = realm.where(RealmNews::class.java).findAll()
                realmNews?.let {
                    realmNews.deleteAllFromRealm()
                }
            })
        } finally {
            realmManager.closeRealm(realm)
        }
    }


    /**
     * Saves News
     */
    override fun storeNews(newsList: List<NewsEntity>) {
        val realm = realmManager.realm
        try {
            for (newsEntity in newsList) {
                val realmNewsToStore = createRealmNews(newsEntity)
                realm.executeTransaction({
                    val realmNews = realm.where(RealmNews::class.java).findFirst()
                    realmNews.let {
                        realm.copyToRealmOrUpdate(realmNewsToStore)
                    }
                })
            }
            pref.putBooleanData(IS_LOCAL_DATA_SAVED, true)
        } finally {
            realmManager.closeRealm(realm)
        }
    }

    /**
     *  Reads for the locally saved news and converts RealmNews to News
     */
    override fun getNews(): Observable<List<NewsEntity>> {
        val newsList = ArrayList<NewsEntity>()
        val realm = realmManager.realm
        try {
            val realmNewsList = realm.where(RealmNews::class.java)?.findAll()
            realmNewsList?.let { list ->
                for (news in list) {
                    val createdNews = news.asNewsModel()
                    newsList.add(createdNews)
                }
            }
            return Observable.just(newsList)
        } finally {
            realmManager.closeRealm(realm)
        }
    }

    /**
     * Looks for the locally saved news with specific title and converts RealmNews to News
     */
    override fun getNews(newsTitle: String): Observable<NewsEntity> {
        val realm = realmManager.realm
        try {
            val realmNews = realm.where(RealmNews::class.java)
                    .equalTo("title", newsTitle).findFirst()
            realmNews?.let { news ->
                val createdNews = news.asNewsModel()
                return Observable.just(createdNews)
            }
            return Observable.just(null)
        } finally {
            realmManager.closeRealm(realm)
        }
    }

    /**
     * It converts News --> RealmNews
     */
    private fun createRealmNews(news: NewsEntity): RealmNews {
        val multimedia = if (news.multimedia != null && news.multimedia.isNotEmpty()) {
            val multimediaRealmList = RealmList<RealmMultimedia>()
            for (media in news.multimedia) {
                multimediaRealmList.add(RealmMultimedia(media.url, media.format, media.height, media.width, media.type, media.subtype, media.caption, media.copyright))
            }
            multimediaRealmList
        } else {
            RealmList()
        }
        return RealmNews(news.section, news.title, news.information, news.url, news.byline, news.item_type, news.material_type_facet, news.published_date, multimedia)
    }
}
