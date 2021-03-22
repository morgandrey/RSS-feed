package com.example.rss_feed.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

object Utils {
    fun convertToDateViaInstant(dateToConvert: LocalDateTime): Date {
        return Date.from(
            dateToConvert
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
    }
}