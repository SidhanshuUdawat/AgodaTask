package com.agodatask.model

import com.agodatask.datasets.NewsMultimedia
import io.realm.RealmObject

/**
 * Created by Sid on 27/06/2018.
 */

open class RealmMultimedia(var url: String = "",
                           var format: String = "",
                           var height: Int = 0,
                           var width: Int = 0,
                           var type: String = "",
                           var subtype: String = "",
                           var caption: String = "",
                           var copyright: String = "") : RealmObject() {


    fun asMultimediaModel(): NewsMultimedia {
        return NewsMultimedia(url, format, height, width, type, subtype, caption, copyright)
    }
}