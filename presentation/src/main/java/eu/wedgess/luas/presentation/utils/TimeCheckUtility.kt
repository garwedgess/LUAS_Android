package eu.wedgess.luas.presentation.utils

import java.util.*

/**
 * Time utility class so it can be mocked in unit test TramForecastViewModel
 */
open class TimeCheckUtility {

    fun getHourAndMinutesOfDay(): Map<String, Int> {
        Calendar.getInstance().apply {
            val hour = get(Calendar.HOUR_OF_DAY)
            val minutes = get(Calendar.MINUTE)
            return mapOf(HOUR to hour, MINUTES to minutes)
        }
    }

    companion object {
        const val HOUR = "hour"
        const val MINUTES = "minutes"
    }

}