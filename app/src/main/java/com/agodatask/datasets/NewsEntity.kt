package com.agodatask.datasets

import com.google.gson.annotations.SerializedName

/**
 * Created by Sid on 26/06/2018.
 */

data class NewsEntity(val section: String,
                      val title: String,
                      @SerializedName("abstract") val information: String,
                      val url: String?,
                      val byline: String?,
                      val item_type: String,
                      val material_type_facet: String,
                      val published_date: String,
                      val multimedia: Object)