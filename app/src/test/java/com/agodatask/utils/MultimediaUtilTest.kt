package com.agodatask.utils

import com.agodatask.datasets.NewsMultimedia
import com.agodatask.util.TestUtil
import junit.framework.Assert
import org.junit.Test


/**
 * Created by Sid on 30/06/2018.
 */

class MultimediaUtilTest {

    @Test
    fun it_gets_image_url_of_format_thumbLarge() {

        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, "thumbLarge")
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, "Standard Thumbnail")
        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)

        val url = MultimediaUtil.getNewsImageUrl(multimediaList, "thumbLarge")
        Assert.assertEquals(url, imageURL1)
    }

    @Test
    fun it_gets_image_url_of_format_standard_thumbnail() {

        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, "thumbLarge")
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, "Standard Thumbnail")
        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)

        val url = MultimediaUtil.getNewsImageUrl(multimediaList, "Standard Thumbnail")
        Assert.assertEquals(url, imageURL2)
    }

    @Test
    fun it_gets_image_url_of_format_normal() {

        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"
        val imageURL3 = "http://myimage3.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, "thumbLarge")
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, "Standard Thumbnail")
        val multimedia3 = TestUtil.createNewMultimedia(imageURL3, "Normal")

        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)
        multimediaList.add(multimedia3)

        val url = MultimediaUtil.getNewsImageUrl(multimediaList, "Normal")
        Assert.assertEquals(url, imageURL3)
    }

    @Test
    fun it_gets_image_url_of_format_medium_three_by_two() {

        val imageURL1 = "http://myimage1.com"
        val imageURL2 = "http://myimage2.com"
        val imageURL3 = "http://myimage3.com"

        val multimediaList = ArrayList<NewsMultimedia>()
        val multimedia1 = TestUtil.createNewMultimedia(imageURL1, "thumbLarge")
        val multimedia2 = TestUtil.createNewMultimedia(imageURL2, "mediumThreeByTwo210")
        val multimedia3 = TestUtil.createNewMultimedia(imageURL3, "Normal")

        multimediaList.add(multimedia1)
        multimediaList.add(multimedia2)
        multimediaList.add(multimedia3)

        val url = MultimediaUtil.getNewsImageUrl(multimediaList, "mediumThreeByTwo210")
        Assert.assertEquals(url, imageURL2)
    }
}