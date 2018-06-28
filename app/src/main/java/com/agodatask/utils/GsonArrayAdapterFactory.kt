package com.agodatask.utils


import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType


/**
 * Created by Sid on 28/06/2018.
 */

class GsonArrayAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        var typeAdapter: TypeAdapter<T>? = null
        try {
            if (type?.rawType === List::class.java) {
                return GsonArrayAdapter((type.type as ParameterizedType).actualTypeArguments[0] as Class<T>) as TypeAdapter<T>
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return typeAdapter
    }

}