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
    private var mRadius = 0f       // 内圆半径
    private var isDrawText = false      // 是否显示文本
    private var mText = ""              // 文本：伤害、治疗量等
    private var mTextDelta = 0          // 文本位置偏移量
    private var mTextAlpha = 0         // 文本显示的透明度
    private var isDrawMp = false       // 是否绘制灵力
    private val maxMp = 300            // 最大灵力
    private var mpPercent = 0f         // 当前灵力值/最大灵力值

    init{
        val mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.CharacterView)
        mRadius = dip(28f).toFloat()
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

        // 移动原点到中心
        canvas.translate((canvas.width / 2).toFloat(), (canvas.width / 2).toFloat())

        // 绘制白色背景
        mPaint.color = resources.getColor(R.color.colorWhite)
        canvas.drawCircle(0f, 0f, mRadius, mPaint)

        // 绘制灵力
        if(isDrawMp) {
            mPaint.color = resources.getColor(R.color.colorMp)
            mPaint.alpha = 200
            mPaint.strokeWidth = 1f
            mpPercent = character.attr["mp"]!!.toFloat() / maxMp
            val rect = Rect(-mRadius.toInt(), (mRadius - mpPercent * 2 * mRadius).toInt(), mRadius.toInt(), mRadius.toInt())
            canvas.drawRect(rect, mPaint)
        }

        // 绘制圆环
        val r1 = dip(4f)
        val r2 = dip(6f)
        val r3 = dip(12f)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = r3.toFloat()

        // 绘制浅色圆环
        var oval = RectF(- mRadius - r2, - mRadius - r2, mRadius + r2, mRadius + r2)
        mPaint.color = resources.getColor(R.color.colorScene)
        canvas.drawArc(oval, -90f, 360f, false, mPaint)

        mPaint.strokeWidth = r2.toFloat()
        // 绘制血条
        val hpAngle = (character.attr["hp"]!! / character.attr["maxHp"]!!.toFloat()) * 360
        oval = RectF(- mRadius - r1, - mRadius - r1, mRadius + r1, mRadius + r1)
        mPaint.color = resources.getColor(R.color.colorHpGreen)
        if (character.isNpc)
            mPaint.color = resources.getColor(R.color.colorHpRed)
        canvas.drawArc(oval, -90f, hpAngle, false, mPaint)

        mPaint.strokeWidth = r2.toFloat()
        // 绘制深色圆环
        oval = RectF(- mRadius - r2, - mRadius - r2, mRadius + r2, mRadius+ r2)
        mPaint.color = resources.getColor(R.color.colorPanel)
        canvas.drawArc(oval, -90f, 360f, false, mPaint)

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

            canvas.drawText(text, -(bounds.width() / 2).toFloat() + mTextDelta / 2, - mRadius - mTextDelta, mPaint)
        }
        canvas.save()
    }

    private fun growMp() {
        isDrawMp = true
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 30 * 1000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = -1
        val initMp = character.attr["mp"]!!
        animator.addUpdateListener { animation ->
            if (character.attr["mp"]!! < 300)
                character.attr["mp"] = initMp + (maxMp * animation.animatedValue as Float).toInt()
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