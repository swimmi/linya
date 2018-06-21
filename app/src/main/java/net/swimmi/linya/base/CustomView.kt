package net.swimmi.linya.base

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout

open class CustomView(context: Context, attrs: AttributeSet?): RelativeLayout(context, attrs) {

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }

    var index = -1
        get() = (parent as ViewGroup).indexOfChild(this)
}