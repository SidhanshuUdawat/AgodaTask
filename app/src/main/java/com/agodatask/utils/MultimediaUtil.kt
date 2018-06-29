package com.agodatask.utils

import com.agodatask.datasets.NewsMultimedia

/**
 * Created by Sid on 30/06/2018.
 */
class MultimediaUtil {

    companion object {

        /**
         * Api provides list of images to display and their may not always be correct
         * in order to display the most optimum image to display list is filter using dimension as 640
         */

        fun getNewsImageUrl(multimediaList: List<NewsMultimedia>?, imageFormat: String): String {
            return if (multimediaList != null && multimediaList.isNotEmpty()) {
                val imageURL = multimediaList.filter { image -> image.format.toLowerCase().contentEquals(imageFormat.toLowerCase()) }
                if (imageURL.isNotEmpty()) {
                    imageURL[0].url
                } else {
                    multimediaList[0].url
                }
            } else {
                ""
            }
        }
    }
}