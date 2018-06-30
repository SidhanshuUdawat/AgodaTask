package com.agodatask.util

import com.agodatask.datasets.NewsEntity
import com.agodatask.datasets.NewsMultimedia

/**
 * Created by Sid on 30/06/2018.
 */

class TestUtil {

    companion object {
        fun createNews(multimedia: ArrayList<NewsMultimedia> = ArrayList()): NewsEntity {
            return NewsEntity("Some section", "some title",
                    "Some information", "http://detail.com",
                    "some by line", "Some item type",
                    "some data", "10th July 2018",
                    multimedia)
        }

        fun createNewMultimedia(imageUrl: String, format: String): NewsMultimedia {
            return NewsMultimedia(imageUrl, format,
                    100, 100,
                    "image", "photo",
                    "some caption", "some copy right")
        }
    }
}