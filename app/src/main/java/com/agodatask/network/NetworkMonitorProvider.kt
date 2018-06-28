package com.agodatask.network

/**
 * Created by Sid on 26/06/2018.
 */

interface NetworkMonitorProvider {
    fun isConnected(): Boolean
}