package net.swimmi.linya.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import net.swimmi.linya.base.ViewHolder

abstract class AdpCommon<T>(context:Context, mData:List<T>, itemLayoutId:Int):BaseAdapter() {
    private var mInflater:LayoutInflater
    private var mContext:Context = context
    private var mData:List<T>
    private var mItemLayoutId:Int = 0

    init{
        this.mInflater = LayoutInflater.from(mContext)
        this.mData = mData
        this.mItemLayoutId = itemLayoutId
    }
    override fun getCount(): Int {
        return mData.size
    }
    override fun getItem(position: Int): T {
        return mData[position]
    }
    override fun getItemId(position:Int):Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = getViewHolder(position, convertView, parent)
        convert(viewHolder, getItem(position), position)
        return viewHolder.getConvertView()
    }

    abstract fun convert(helper: ViewHolder, item: T, position: Int)

    private fun getViewHolder(position:Int, convertView:View?, parent:ViewGroup):ViewHolder {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position)
    }
}