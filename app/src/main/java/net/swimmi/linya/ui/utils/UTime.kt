package net.swimmi.linya.ui.utils

import java.text.SimpleDateFormat
import java.util.*

class UTime {

    fun getNowString(): String {
        val format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format, Locale.CHINESE)
        return sdf.format(Date())
    }
}