package com.example.playground.codetest

import android.telephony.PhoneNumberUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val BRAZIL_COUNTRY_AREA_CODE = "+55"

fun String.toDateFromRestUtc(): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun String.toDateFromUTC(): Date? {
    return try {
        SimpleDateFormat(
            "yyyy-MM-dd'T'hh:mm:ss",
            Locale.getDefault()
        ).parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun String.toDateOrNull(): Date? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun String?.formatToBrazilPhoneNumber(): String {
    return PhoneNumberUtils.formatNumber(this, "BR")
}

fun String.toCapitalizeWords(): String {
    val words = this.toLowerCase(defaultLocale).split(" ").toMutableList()
    var output = ""

    for (word in words) {
        output += word.capitalize(defaultLocale) + " "
    }

    return output.trim()
}