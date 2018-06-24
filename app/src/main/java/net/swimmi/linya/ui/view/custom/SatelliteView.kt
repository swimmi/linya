package net.swimmi.linya.ui.view.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewManager
import android.view.animation.LinearInterpolator
import net.swimmi.linya.R
import net.swimmi.linya.ui.utils.UDisplay
import org.jetbrains.anko.custom.ankoView

class SatelliteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle:Int = 0): View(context, attrs, defStyle) {
    private val TAG = "SatelliteView"

    private lateinit var circle: Paint
    private var strokeWidth = 3f
    private var angle = 0f
    private var r = 0f //半径
    private var smallBallWidth = 16f
    private var color = -1
    private var width = 0f
    private lateinit var animator: ValueAnimator
    /***
     * 是否绘制小圆点
     */
    private var isDrawPoint = false
    init{
        val mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.SatelliteView)
        width = mTypeArray.getDimension(R.styleable.SatelliteView_circleWidth, 150f)
        color = mTypeArray.getResourceId(R.styleable.SatelliteView_circleColor, -1)
        initDrawable()
        mTypeArray.recycle()
    }

    private fun initDrawable() {
        circle = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        circle.color = resources.getColor(color)
        circle.style = Paint.Style.STROKE
        circle.isAntiAlias = true
        circle.strokeWidth = strokeWidth
        r = width / 2
        /************* 外层圆环 ***********/
        //canvas.drawCircle(r + 5, r + 5, r, circle)
        /********** 外层点 *****************/
        circle.color = resources.getColor(color)
        circle.style = Paint.Style.FILL
        circle.strokeWidth = strokeWidth
        mathDisc(canvas)
    }
    /********
     * 计算圆弧点上的坐标
     * 公式:Math.sin(x) x 的正玄值。返回值在 -1.0 到 1.0 之间；
     * Math.cos(x) x 的余弦值。返回的是 -1.0 到 1.0 之间的数；
     * <p>
     * X指的是弧度 因此
     * <p>
     * 30° 角度 的弧度 = 2*PI/360*30
     * <p>
     * 圆上每个点的X坐标=a + Math.sin(2*Math.PI / 360) * r
     * Y坐标=b + Math.cos(2*Math.PI / 360) * r ；
     **************/
    private fun mathDisc(canvas: Canvas) {
        if (isDrawPoint)
        {
            val radian = ((2 * Math.PI / 360) * (angle)).toFloat() // 360/8=45,即45度(这个随个人设)
            val x = (r.toDouble() + Math.sin(radian.toDouble()) * r).toFloat() // r 是圆形中心的坐标X 即定位left 的值
            val y = (r - Math.cos(radian.toDouble()) * r).toFloat() // r 是圆形中心的坐标Y 即定位top 的值
            canvas.translate(canvas.width / 2 - r, canvas.height / 2 - r + UDisplay(context).d2p(4f))
            canvas.drawCircle(x, y, smallBallWidth, circle)
            canvas.save()
        }
    }
    //属性动画
    fun startAnimation(speed: Long) {
        isDrawPoint = true
        animator = ValueAnimator.ofFloat(0f, 1.0f)
        //动画时长，让进度条在CountDown时间内正好从0-360走完，
        animator.duration = speed
        animator.interpolator = LinearInterpolator()//匀速
        animator.repeatCount = -1//-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener { animation ->
            /**
             * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
             * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
             * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
             */
            /**
             * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
             * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
             * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
             */
            angle = 360 * animation.animatedValue as Float
            invalidate()//实时刷新view，这样我们的进度条弧度就动起来了
        }
        //开启动画
        animator.start()
    }
    fun stopAnimation() {
        animator.cancel()
    }
}