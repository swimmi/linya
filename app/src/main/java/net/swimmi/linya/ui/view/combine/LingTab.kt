package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.ling_tab.view.*
import net.swimmi.linya.R
import net.swimmi.linya.data.DatConst


class LingTab(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.ling_tab, this)
        if(attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.LingTab)
            for (i in 0..ta.indexCount) {
                val itemId: Int = ta.getIndex(i)
                when (itemId) {
                    R.styleable.LingTab_ling -> {
                        v_ling.text = DatConst.PARTNER_LINGS[ta.getInt(itemId, 0)]
                        v_ling.setBackgroundResource(DatConst.PARTNER_LING_ICONS[ta.getInteger(itemId, 0)])
                    }
                }
            }
            ta.recycle()
        }
    }

    fun setLing(ling: Int) {
        v_ling.setBackgroundResource(DatConst.PARTNER_LING_ICONS[ling])
        v_ling.text = DatConst.PARTNER_LINGS[ling]
    }

}