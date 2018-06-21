package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.number_tag.view.*
import net.swimmi.linya.R


class NumberTag(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.number_tag, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberTag)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.NumberTag_value -> tv_value.text = ta.getInteger(itemId, 0).toString()
                R.styleable.NumberTag_icon -> iv_icon.setImageResource(ta.getResourceId(itemId, -1))
            }
        }
        ta.recycle()
    }
}