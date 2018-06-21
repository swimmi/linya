package net.swimmi.linya.ui.view.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import net.swimmi.linya.R
import net.swimmi.linya.ui.utils.UDisplay

class RippleView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle:Int = 0): View(context, attrs, defStyle) {
    private val TAG = "RippleView"

    private lateinit var paint: Paint
    private var strokeWidth = 3f
    private var color = -1
    private var width = 0f
    private var largen = 0f   //圆变大尺寸
    private var distance = 0f  //内外圆距离
    private var mAlpha = 200  //透明度
    private lateinit var animator: ValueAnimator
    private var autoStart = true
    private var isDrawPoint = false
    init{
        val mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView)
        width = mTypeArray.getDimension(R.styleable.RippleView_rippleWidth, 30f)
        color = mTypeArray.getResourceId(R.styleable.RippleView_rippleColor, -1)
        autoStart = mTypeArray.getBoolean(R.styleable.RippleView_autoStart, true)
        initDrawable()
        if (autoStart) startAnimation()
        mTypeArray.recycle()
    }

    private fun initDrawable() {
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = resources.getColor(color)
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = strokeWidth
        mathDisc(canvas)
    }

    private fun mathDisc(canvas:Canvas) {
        if (isDrawPoint)
        {
            paint.alpha = mAlpha
            canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(),width / 2 + largen, paint) // 内圆
            canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(),width / 2 + largen + distance , paint) // 外圆
            canvas.save()
        }
    }

    //属性动画
    fun startAnimation() {
        isDrawPoint = true
        animator = ValueAnimator.ofFloat(0f, 1.0f)
        animator.duration = 1200
        animator.interpolator = DecelerateInterpolator()
        animator.repeatCount = 0
        animator.addUpdateListener { animation ->
            largen = 12 * animation.animatedValue as Float
            distance = 8 * animation.animatedValue as Float
            mAlpha = (200 - (200 * animation.animatedValue as Float)).toInt()

            invalidate()    // 实时刷新view
        }

        // 开启动画
        animator.start()
    }
    fun stopAnimation() {
        animator.cancel()
    }
}