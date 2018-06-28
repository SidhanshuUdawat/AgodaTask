package com.agodatask.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Sid on 26/06/2018.
 *
 * Provides Network Monitoring capabilities
 */

class LiveNetworkMonitor(val context: Context) : NetworkMonitorProvider {

    /**
     * Checks for the internet connection
     */
    override fun isConnected(): Boolean {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}