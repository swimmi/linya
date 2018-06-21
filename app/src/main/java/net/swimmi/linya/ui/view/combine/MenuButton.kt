package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.menu_button.view.*
import net.swimmi.linya.R


class MenuButton(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.menu_button, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuButton)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.MenuButton_text -> tv_name.setText(ta.getResourceId(itemId, -1))
                R.styleable.MenuButton_hasDot -> v_dot.visibility = if(ta.getBoolean(itemId, false)) View.VISIBLE else View.GONE
            }
        }
        ta.recycle()
    }

}