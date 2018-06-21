package net.swimmi.linya.ui.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class UFile {

    fun readText(context: Context, fileName: String): String {
        val sb = StringBuilder()
        try {
            val am = context.assets
            val br = BufferedReader(InputStreamReader(am.open(fileName)))
            var line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}