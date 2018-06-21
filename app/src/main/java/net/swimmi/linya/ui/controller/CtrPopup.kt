package net.swimmi.linya.ui.controller

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow

class CtrPopup
(
        private var context: Context,
        private var popupWindow: PopupWindow
) {
    private var layoutResId: Int = 0
    lateinit var mPopupView: View
    private var mView: View? = null
    private lateinit var mWindow: Window

    fun setView(layoutResId: Int) {
        this.mView = LayoutInflater.from(context).inflate(layoutResId, null)
        this.layoutResId = layoutResId
        installContent()
    }

    fun setView(view: View) {
        this.mView = view
        this.layoutResId = 0
        installContent()
    }

    private fun installContent() {
        when {
            layoutResId != 0 -> mPopupView = LayoutInflater.from(context).inflate(layoutResId, null)
            mView != null -> mPopupView = mView!!
        }
        popupWindow.contentView = mPopupView
    }

    /**
     * 设置宽高
     * [width] 宽度
     * [height] 高度
     */
    private fun setWidthAndHeight(width: Int, height: Int) {
        when{
            width == 0 || height == 0 -> {
                popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
                popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            else -> {
                popupWindow.width = width
                popupWindow.height = height
            }
        }
    }

    /**
     * 设置背景灰色程度
     * [level] 0.0f-1.0f
     */
    fun setBackgroundDarkLevel(level: Float) {
        mWindow = (context as Activity).window
        val params = mWindow.attributes
        params.alpha = level
        mWindow.attributes = params
    }

    /**
     * 设置动画
     */
    private fun setAnimationStyle(animationStyle: Int) {
        popupWindow.animationStyle = animationStyle
    }

    /**
     * 设置外部是否可点击
     */
    private fun setOutsizeTouchable(touchable: Boolean) {
        popupWindow.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindow.isOutsideTouchable = touchable
        popupWindow.isFocusable = touchable
    }

    class PopupParams(context: Context) {
        var mContext: Context = context
        var layoutResId: Int = 0
        lateinit var context: Context
        var mWidth = 0
        var mHeight = 0
        var isShowBg: Boolean = false
        var isShowAnim: Boolean = false
        var bgDarkLevel = 0f
        var animationStyle: Int = 0
        var mView: View? = null
        var isTouchable = false

        fun apply(controller: CtrPopup) {
            when {
                mView != null -> controller.setView(mView!!)
                layoutResId != 0 -> controller.setView(layoutResId)
                else -> throw IllegalArgumentException("PopupView's contentView is null")
            }
            controller.setWidthAndHeight(mWidth, mHeight)
            controller.setOutsizeTouchable(isTouchable)
            if(isShowBg) controller.setBackgroundDarkLevel(bgDarkLevel)
            if(isShowAnim) controller.setAnimationStyle(animationStyle)
        }
    }
}