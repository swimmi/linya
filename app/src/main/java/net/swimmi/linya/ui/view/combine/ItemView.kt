package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.item_view.view.*
import net.swimmi.linya.R


class ItemView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_view, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.ItemView_name -> tv_name.text = ta.getText(itemId)
                R.styleable.ItemView_count -> tv_count.text = ta.getInt(itemId, 1).toString()
            }
        }
        ta.recycle()
    }


    var name
        get() = tv_name.text
        set(value) { tv_name.text  = value }

    var count
        get() = tv_count.text.toString().toInt()
        set(value) { tv_count.text  = value.toString() }
}