package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.simple_button.view.*
import net.swimmi.linya.R


class SimpleButton(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.simple_button, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleButton)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.SimpleButton_text -> tv_text.text = ta.getString(itemId)
                R.styleable.SimpleButton_backgroundResource -> tv_text.setBackgroundResource(ta.getResourceId(itemId, -1))
            }
        }
        ta.recycle()
        this.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val animation = AnimationUtils.loadAnimation(context, R.anim.zoom)
                    view.startAnimation(animation)
                }
            }
            return@setOnTouchListener false
        }
    }

}