package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.catalog_tab.view.*
import net.swimmi.linya.R


class CatalogTab(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.catalog_tab, this)
        if(attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CatalogTab)
            for (i in 0..ta.indexCount) {
                val itemId: Int = ta.getIndex(i)
                when (itemId) {
                    R.styleable.CatalogTab_name -> tv_name.text = ta.getText(itemId)
                    R.styleable.CatalogTab_onlyIcon -> tv_name.visibility = View.GONE
                }
            }
            ta.recycle()
        }
    }

    fun setIcon(resId: Int) {
        iv_icon.setImageResource(resId)
    }

    var onlyIcon
        get() = tv_name.visibility == View.GONE
        set(value) { if(value) tv_name.visibility = View.GONE }

    var name
        get() = tv_name.text
        set(value) { tv_name.text  = value }
}