package com.example.fleetech.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import java.util.*

class AddressUtils(private val context: Context) {

    fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addressText = ""

        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val sb = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append("\n")
                }
                addressText = sb.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return addressText
    }
}