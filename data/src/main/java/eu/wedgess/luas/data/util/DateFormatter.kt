package eu.wedgess.luas.data.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat

// use ThreadLocal to make SimpleDateFormat thread safe

val uiDateFormatter = object : ThreadLocal<DateFormat>() {
    @SuppressLint("SimpleDateFormat")
    override fun initialValue(): DateFormat? {
        return SimpleDateFormat("EEEE dd MMM yyyy   HH:mm:ss")
    }
}

val responseDateFormatter = object : ThreadLocal<DateFormat>() {
    @SuppressLint("SimpleDateFormat")
    override fun initialValue(): DateFormat? {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    }
}