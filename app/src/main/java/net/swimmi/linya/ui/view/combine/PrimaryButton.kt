package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.primary_button.view.*
import net.swimmi.linya.R


class PrimaryButton(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.primary_button, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PrimaryButton)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.PrimaryButton_text -> tv_text.text = ta.getString(itemId)
                R.styleable.PrimaryButton_isPrimary -> when(ta.getBoolean(itemId, true)) {
                    true -> {
                        tv_text.setBackgroundResource(R.drawable.btn_primary)
                    }
                    false -> {
                        tv_text.setBackgroundResource(R.drawable.btn_vice)
                    }
                }
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