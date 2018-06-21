package net.swimmi.linya.ui.view.custom

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.PopupWindow
import net.swimmi.linya.ui.controller.CtrPopup

class PopupView(var context: Context) : PopupWindow() {

    var controller: CtrPopup = CtrPopup(context, this)
    var tag: String = ""
    lateinit var view: View
    private lateinit var mWindow: Window

    override fun getWidth(): Int {
        return controller.mPopupView.measuredWidth
    }

    override fun getHeight(): Int {
        return controller.mPopupView.measuredHeight
    }

    interface ViewInterface {
        fun getChildView(view: View, layoutResId: Int)
    }

    fun setBackgroundDarkLevel(level: Float) {
        mWindow = (context as Activity).window
        val params = mWindow.attributes
        params.alpha = level
        mWindow.attributes = params
    }

    override fun dismiss() {
        super.dismiss()
        if(tag == "first")
            controller.setBackgroundDarkLevel(1.0f)
    }

    class Builder(var context: Context) {
        private var params: CtrPopup.PopupParams = CtrPopup.PopupParams(context)
        var listener: ViewInterface? = null

        /**
         * [layoutResId] 布局ID
         */
        fun setView(layoutResId: Int): Builder {
            params.mView = LayoutInflater.from(context).inflate(layoutResId, null)
            params.layoutResId = layoutResId
            return this
        }

        fun setView(view: View): Builder {
            params.mView = view
            params.layoutResId = 0
            return this
        }

        fun setViewOnClickListener(listener: ViewInterface): Builder {
            this.listener = listener
            return this
        }

        fun setWidthAndHeight(width: Int, height: Int): Builder {
            params.mWidth = width
            params.mHeight = height
            return this
        }

        fun setBackgroundDarkLevel(level: Float): Builder {
            params.isShowBg = true
            params.bgDarkLevel = level
            return this
        }

        fun setOutsideTouchable(touchable: Boolean): Builder {
            params.isTouchable = touchable
            return this
        }

        fun setAnimationStyle(animationStyle: Int): Builder {
            params.isShowAnim = true
            params.animationStyle = animationStyle
            return this
        }

        fun create(): PopupView {
            val popupWindow = PopupView(params.mContext)
            params.apply(popupWindow.controller)
            if (listener != null && params.layoutResId != 0)
                listener!!.getChildView(popupWindow.controller.mPopupView, params.layoutResId)
            return popupWindow
        }
    }

}