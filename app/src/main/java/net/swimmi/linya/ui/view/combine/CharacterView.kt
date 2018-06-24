package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.character_view.view.*
import net.swimmi.linya.R

class CharacterView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    private val tag = "CharacterView"

    init {
        LayoutInflater.from(context).inflate(R.layout.character_view, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CharacterView)
        for ( i in 0 until ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.CharacterView_name -> tv_name.text = ta.getString(itemId)
                R.styleable.CharacterView_maxHp -> pb_hp.max = ta.getInt(itemId, 0)
                R.styleable.CharacterView_hp -> pb_hp.progress = ta.getInt(itemId, 0)
            }
        }
        ta.recycle()
    }

    var enable = true

    var maxHp
        get() = pb_hp.max
        set(value) { pb_hp.max = value }

    var hp
        get() = pb_hp.progress
        set(value) { pb_hp.progress = value }
}