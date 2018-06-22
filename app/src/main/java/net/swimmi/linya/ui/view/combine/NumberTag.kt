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
                R.styleable.NumberTag_text -> tv_value.text = ta.getText(itemId)
                R.styleable.NumberTag_icon -> iv_icon.setImageResource(ta.getResourceId(itemId, -1))
            }
        }
        ta.recycle()
    }

    var text: String
        get() = tv_value.text as String
        set(value) {
            val base = 10000
            if (value.toLong() < base)
                tv_value.text = value
            else
                tv_value.text = String.format(resources.getString(R.string.fmt_money), value.toLong() / base)
        }
}