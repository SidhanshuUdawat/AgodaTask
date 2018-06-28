package com.agodatask.model

import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by Sid on 27/06/2018.
 */

open class RealmNews(var section: String = "",
                     var title: String = "",
                     var abstract: String = "",
                     var url: String? = "",
                     var byline: String? = "",
                     var item_type: String = "",
                     var material_type_facet: String = "",
                     var published_date: String = "",
                     var multimedia: RealmList<RealmMultimedia> = RealmList()) : RealmObject() {

    @PrimaryKey
    var mUuid: String = UUID.randomUUID().toString()

    fun asNewsModel(): NewsEntity {
        var multimedia = ArrayList<NewsMultimedia>()
        for (realmImages in this.multimedia) {
            multimedia.add(realmImages.asMultimediaModel())
        }
        return NewsEntity(section, title, abstract, url, byline, item_type, material_type_facet, published_date, multimedia)
    }
}
