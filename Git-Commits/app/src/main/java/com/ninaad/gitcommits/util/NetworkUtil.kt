package com.ninaad.gitcommits.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import timber.log.Timber

interface NetworkAvailabilityInterface {
    fun isNetworkAvailable(): Boolean
}

object NetworkUtil {
    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities?.let {
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Timber.i("Cellular Network available")
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Timber.i("Wifi available")
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Timber.i("Ethernet available")
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}