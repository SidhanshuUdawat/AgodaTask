package com.agodatask.datasets

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


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
                      val multimedia: List<NewsMultimedia>) {


    companion object {
        fun createMediaDeserializer(): MediaDeserializer = MediaDeserializer()

    }

    /**
     * Custom deserialization is used because in some cases "multimedia" appears as a List and some cases it appears as a String
     */

    class MediaDeserializer : JsonDeserializer<NewsEntity> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): NewsEntity {

            val root = json as JsonObject
            val section = root.getOrElse("section", "")
            val title = root.getOrElse("title", "")
            val information = root.getOrElse("abstract", "")
            val url = root.getOrElse("url", "")
            val byline = root.getOrElse("byline", "")
            val item_type = root.getOrElse("item_type", "")
            val material_type_facet = root.getOrElse("material_type_facet", "")
            val published_date = root.getOrElse("published_date", "")

            val multimedia = try {
                val multimedia = root.getAsJsonArray("multimedia")
                val listType = object : TypeToken<List<NewsMultimedia>>() {}.type
                Gson().fromJson<List<NewsMultimedia>>(multimedia, listType)
            } catch (e: Exception) {
                ArrayList<NewsMultimedia>()
            }

            return NewsEntity(section, title, information, url, byline, item_type, material_type_facet, published_date, multimedia)
        }

        /**
         * An extension funtion is created in order to get default value
         */
        fun JsonObject.getOrElse(key: String, default: String): String = get(key)?.asString
                ?: default
    }

}