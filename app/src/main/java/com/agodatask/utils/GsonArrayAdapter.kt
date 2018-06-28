package com.agodatask.utils


import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter


/**
 * Created by Sid on 28/06/2018.
 */

class GsonArrayAdapter<T>(private val adapterclass: Class<T>) : TypeAdapter<List<T>>() {

    override fun read(`reader`: JsonReader?): List<T> {
        val list = ArrayList<T>()

        val gsonBuilder = GsonBuilder()
                .registerTypeAdapterFactory(GsonArrayAdapterFactory())
                .create()
        if (reader?.peek() === JsonToken.STRING) {
            val data = gsonBuilder.fromJson<T>(reader, adapterclass)
            list.add(data)

        } else if (reader?.peek() == JsonToken.BEGIN_ARRAY) {
            reader.beginArray()
            while (reader.hasNext()) {
                val data = gsonBuilder.fromJson<T>(reader, adapterclass)
                list.add(data)
            }
            reader.endArray()
        }
        return list
    }

    override fun write(out: JsonWriter?, value: List<T>?) {
        // Do nothing
    }

}