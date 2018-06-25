package net.swimmi.linya.ui.view.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import net.swimmi.linya.R
import net.swimmi.linya.model.Character
import org.jetbrains.anko.dip

class CharacterView @JvmOverloads constructor(context: Context, var character: Character, attrs: AttributeSet? = null, defStyle:Int = 0): View(context, attrs, defStyle) {
    private val tag = "CharacterView"

    val size = dip(100f)

    private lateinit var mPaint: Paint   // 画板
    private val ringWidth = dip(4f).toFloat()         // 圆环宽度
    private val ringRadius = dip(32f).toFloat()       // 圆环内圆半径
    private var isDrawText = false      // 是否显示文本
    private var mText = ""              // 文本：伤害、治疗量等
    private var mTextDelta = 0          // 文本位置偏移量
    private var mTextAlpha = 0         // 文本显示的透明度
    private var isDrawMp = false       // 是否绘制灵力
    private var maxMp = 300            // 灵力最大值
    private var mpPercent = 0f         // 当前灵力值

    init{
        val mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.CharacterView)

        initDrawable()
        mTypeArray.recycle()
        growMp()
    }

    private fun initDrawable() {
        mPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        canvas.translate((canvas.width / 2).toFloat(), (canvas.width / 2).toFloat())  // 移动原点到中心
        // 绘制边线
        mPaint.color = resources.getColor(R.color.colorPanel)
        canvas.drawCircle(0f, 0f, ringRadius + dip(3f), mPaint)
        // 绘制背景
        mPaint.color = resources.getColor(R.color.colorWhite)
        canvas.drawCircle(0f, 0f, ringRadius - dip(6f), mPaint)
        // 绘制灵力
        if(isDrawMp) {
            mPaint.color = resources.getColor(R.color.colorMp)
            mPaint.alpha = 150
            mPaint.style = Paint.Style.FILL
            val rw = ringRadius - dip(6f)
            val rect = Rect(-rw.toInt(), (rw - mpPercent * 2 * rw).toInt(), rw.toInt(), rw.toInt())

            canvas.drawRect(rect, mPaint)
        }
        var oval = RectF(-ringRadius + dip(2f), -ringRadius + dip(2f), ringRadius - dip(2f), ringRadius - dip(2f))

        mPaint.color = resources.getColor(R.color.colorPopupBackground)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = ringWidth
        canvas.drawArc(oval, -90f, 360f, false, mPaint)
        // 绘制血条
            // 血条
        oval = RectF(-ringRadius - dip(3f), -ringRadius - dip(3f), ringRadius + dip(3f), ringRadius + dip(3f))
        val hpAngle = (character.attr["hp"]!! / character.attr["maxHp"]!!.toFloat()) * 360
        mPaint.color = resources.getColor(R.color.colorHp)
        canvas.drawArc(oval, -90f, hpAngle, false, mPaint)

        // 绘制名字
        val bounds = Rect()
        val name = "哈哈哈"
        mPaint.color = resources.getColor(R.color.colorScene)
        mPaint.strokeWidth = 1f
        mPaint.textSize = dip(12).toFloat()
        mPaint.getTextBounds(name, 0, name.length, bounds)
        canvas.drawText(name, -(bounds.width() / 2).toFloat(),  (bounds.height() / 2).toFloat(), mPaint)

        if(isDrawText) {
            mPaint.color = resources.getColor(R.color.colorDamage)
            mPaint.alpha = mTextAlpha
            mPaint.strokeWidth = 3f
            mPaint.textSize = dip(16).toFloat()

            val text = mText
            mPaint.getTextBounds(text, 0, text.length, bounds)

            canvas.drawText(text, -(bounds.width() / 2).toFloat() + mTextDelta / 2, -ringRadius - mTextDelta, mPaint)
        }
        canvas.save()
    }

    private fun growMp(initMp: Int = 0) {
        isDrawMp = true
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 30000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = 0
        animator.addUpdateListener { animation ->
            mpPercent = animation.animatedValue as Float
            invalidate()
        }
        //开启动画
        animator.start()
    }

    fun riseText(text: String) {
        isDrawText = true
        mText = text
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 2000
        animator.interpolator = OvershootInterpolator()
        animator.repeatCount = 0
        animator.addUpdateListener { animation ->
            mTextAlpha = (255 - 255 * (animation.animatedValue as Float)).toInt()
            mTextDelta = dip(10 * animation.animatedValue as Float)
            if (mTextAlpha == 0)
                isDrawText= false
            invalidate()
        }
        //开启动画
        animator.start()
    }

    var enable = true

}