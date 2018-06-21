package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.title_bar.view.*
import net.swimmi.linya.R


class TitleBar(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.TitleBar_text -> tv_name.setText(ta.getResourceId(itemId, -1))
                R.styleable.TitleBar_icon -> iv_icon.setImageResource(ta.getResourceId(itemId, -1))
                R.styleable.TitleBar_textColor -> tv_name.setTextColor(ta.getColor(itemId, -1))
            }
        }
        ta.recycle()
    }
}