package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.random_button.view.*
import net.swimmi.linya.R
import net.swimmi.linya.data.DatConst


class RandomButton(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.random_button, this)
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RandomButton)
        for ( i in 0..ta.indexCount) {
            val itemId: Int = ta.getIndex(i)
            when(itemId) {
                R.styleable.RandomButton_number -> setValues(ta.getInteger(itemId, 0))
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

    private fun setValues(number: Int) {
        tv_name.text = DatConst.RANDOM_NAMES[number]
        tv_name.setBackgroundResource(DatConst.RANDOM_COLOURS[number])
        tv_cost.text = DatConst.RANDOM_COSTS[number].toString()
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled) {
            tv_cost.setBackgroundResource(R.drawable.bg_accent)
            tv_name.isPressed = false
        } else {
            tv_cost.setBackgroundResource(R.drawable.bg_disabled)
            tv_name.isPressed = true
        }
        super.setEnabled(enabled)
    }
}