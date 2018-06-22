package net.swimmi.linya.ui.utils

import android.view.View
import java.util.*

class UFun {
}

fun <T> List<T>.random(): T {
    return this[Random().nextInt(this.size)]
}

fun View.hide() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun View.show() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}