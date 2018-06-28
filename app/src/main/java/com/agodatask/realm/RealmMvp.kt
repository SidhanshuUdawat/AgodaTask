package com.agodatask.realm

import io.realm.Realm

/**
 * Created by Sid on 26/06/2018.
 */

interface RealmMvp {

    val realm: Realm

    fun closeRealm(realm: Realm?)

    fun clearData()

    fun init()
}