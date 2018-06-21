package net.swimmi.linya.ui.utils

import android.content.Context

class UDisplay(var context: Context) {

    fun d2p(d: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (d * scale + 0.5f).toInt()
    }

    fun p2d(p: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (p / scale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        return context.resources.displayMetrics.widthPixels
    }
    fun getScreenHeight(): Int {
        return context.resources.displayMetrics.heightPixels
    }
}