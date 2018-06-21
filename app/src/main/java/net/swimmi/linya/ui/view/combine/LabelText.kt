package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.label_text.view.*
import net.swimmi.linya.R


class LabelText(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.label_text, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelText)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.LabelText_label -> tv_label.text = ta.getText(itemId)
                R.styleable.LabelText_labelColor -> tv_label.setTextColor(ta.getColor(itemId, -1))
                R.styleable.LabelText_text -> tv_text.text = ta.getText(itemId)
                R.styleable.LabelText_appendText -> tv_append_text.text = ta.getText(itemId)
            }
        }
        ta.recycle()
    }
}