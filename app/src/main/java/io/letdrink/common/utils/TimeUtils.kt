package io.letdrink.common.utils

import java.util.*

object TimeUtils {

    val minute = 60000

    fun isMoreThan24HAgo(ms: Long): Boolean {
        val current = System.currentTimeMillis()
        val _24h = minute * 60 * 24
        return current - ms > _24h
    }
}