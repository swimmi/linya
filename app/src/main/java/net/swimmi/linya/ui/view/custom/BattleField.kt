package net.swimmi.linya.ui.view.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.RelativeLayout
import net.swimmi.linya.R
import net.swimmi.linya.model.Character
import org.jetbrains.anko.dip

class BattleField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle:Int = 0): RelativeLayout(context, attrs, defStyle) {
    private val tag = "BattleField"

    private lateinit var mPaint: Paint  // 画板
    private lateinit var animator: ValueAnimator // 执行动画器
    private var isDrawPoint = false
    private var pointX = 0f
    private var pointY = 0f

    init{
        val mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.BattleField)

        initDrawable()
        mTypeArray.recycle()
    }

    private fun initDrawable() {
        mPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {

        if (isDrawPoint) {
            mPaint.color = resources.getColor(R.color.colorRed)
            mPaint.style = Paint.Style.FILL
            canvas.drawCircle(pointX, pointY, 20f, mPaint)
            canvas.save()
        }
    }

    fun shoot(from: CharacterView, to: CharacterView, after: () -> Unit) {
        isDrawPoint = true
        animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 800
        animator.interpolator = DecelerateInterpolator()
        animator.repeatCount = 0
        val deltaX = to.x - from.x
        val deltaY = to.y - from.y
        animator.addUpdateListener { animation ->
            pointX = (from.x + from.size / 2) + (animation.animatedValue as Float) * deltaX
            pointY = (from.y + from.size / 2) + (animation.animatedValue as Float) * deltaY
            if (pointX == to.x + from.size / 2)
                after()
            invalidate()
        }
        //开启动画
        animator.start()
    }
}