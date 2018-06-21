package net.swimmi.linya.ui.view.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import net.swimmi.linya.R

class BiasTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs, android.R.attr.textViewStyle) {

    private var degrees: Float = 0f

    init {
        this.gravity = Gravity.CENTER
        val a = context.obtainStyledAttributes(attrs, R.styleable.BiasTextView)
        degrees = a.getFloat(R.styleable.BiasTextView_degrees, 0f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.save()
        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        canvas.rotate(degrees, width / 2f, height / 2f)
        super.onDraw(canvas)
        canvas.restore()
    }

}