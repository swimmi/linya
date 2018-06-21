package net.swimmi.linya.base

import android.content.Context
import android.graphics.Bitmap
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import net.swimmi.linya.R

class ViewHolder private constructor(context: Context, parent: ViewGroup, layoutId:Int,
                                     position:Int) {
    private val mViews: SparseArray<View>
    private var mPosition: Int = 0
    private var mConvertView: View?
    private var mContext: Context
    init{
        this.mPosition = position
        this.mViews = SparseArray()
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false)
        // setTitle
        mConvertView!!.setTag(R.id.tag_first, this)
        this.mContext = context
    }

    fun getConvertView(): View {
        return mConvertView!!
    }

    fun getPosition(): Int {
        return mPosition
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * [viewId]
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null)
        {
            view = mConvertView!!.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }
    /**
     * 为TextView设置字符串
     *
     * [viewId]
     * [text]
     */
    fun setText(viewId:Int, text:String):ViewHolder {
        val view: TextView = getView<View>(viewId) as TextView
        view.text = text
        return this
    }
    /**
     * 为TextView设置字符串
     *
     * [viewId]
     * [text]
     */
    fun setText(viewId:Int, resId: Int):ViewHolder {
        val view: TextView = getView<View>(viewId) as TextView
        view.text = mContext.getString(resId)
        return this
    }
    /**
     * 为ImageView设置图片
     *
     * [viewId]
     * [drawableId]
     */
    fun setImageResource(viewId: Int, drawableId: Int): ViewHolder {
        val view: ImageView = getView<View>(viewId) as ImageView
        view.setImageResource(drawableId)
        return this
    }
    /**
     * 为ImageView设置图片
     *
     * [viewId]
     * [bmp]
     */
    fun setImageBitmap(viewId: Int, bmp: Bitmap): ViewHolder {
        val view: ImageView = getView<View>(viewId) as ImageView
        view.setImageBitmap(bmp)
        return this
    }

    /**
     * 为View设置背景
     *
     * [viewId]
     * [resId]
     */
    fun setBackground(viewId: Int, resId: Int): ViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(resId)
        return this
    }

    companion object {
        /**
         * 拿到一个ViewHolder对象
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        fun get(context:Context, convertView:View?,
                parent:ViewGroup, layoutId:Int, position:Int):ViewHolder {
            if (convertView == null)
            {
                return ViewHolder(context, parent, layoutId, position)
            }
            return convertView.getTag(R.id.tag_first) as ViewHolder
        }
    }
}