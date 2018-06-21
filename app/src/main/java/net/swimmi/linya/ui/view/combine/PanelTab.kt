package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.catalog_tab.view.*
import net.swimmi.linya.R


class PanelTab(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.panel_tab, this)
        if(attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PanelTab)
            for (i in 0..ta.indexCount) {
                val itemId: Int = ta.getIndex(i)
                when (itemId) {
                    R.styleable.PanelTab_name -> tv_name.text = ta.getText(itemId)
                }
            }
            ta.recycle()
        }
    }

    var name
        get() = tv_name.text
        set(value) { tv_name.text  = value }
}