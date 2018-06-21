package net.swimmi.linya.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_pack.*
import kotlinx.android.synthetic.main.item_view.view.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.data.helper.HItem
import net.swimmi.linya.model.MyItem

class ActPack : AppCompatActivity(), View.OnClickListener {

    private lateinit var mItem: MyItem
    private lateinit var mList: MutableList<MyItem>
    private lateinit var mAdapter: AdpCommon<MyItem>
    private lateinit var mThread: Thread

    private var itemH: HItem = HItem()

    private var theItem = 0
    private var theIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pack)

        initView()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_close -> this.finish()
        }
    }

    private fun initView() {
        mutableListOf(iv_close).forEach {
            it.setOnClickListener(this)
        }
        initBackpack()
    }

    private fun initBackpack() {
        val itemList = mutableListOf(pt_all, pt_material, pt_partner, pt_magic, pt_supply)
        for ( (index, tab) in itemList.withIndex()) {
            tab.setOnClickListener {
                itemList[theIndex].background = getDrawable(R.drawable.bg_tab)

                theIndex = index
                tab.background = getDrawable(R.drawable.bg_tab_on)
                loadItems()
                Handler().postDelayed({ selectItem(0) }, 1000)
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1f
            params.setMargins(6, 12, 6, 0)
            tab.layoutParams = params
            tab.background = getDrawable(R.drawable.bg_tab)
        }

        mList = mutableListOf()
        mAdapter = object: AdpCommon<MyItem>(this, mList, R.layout.item_view) {
            override fun convert(helper: ViewHolder, item: MyItem, position: Int) {
                helper.setText(R.id.tv_count, item.count.toString())
                helper.setText(R.id.tv_name, item.itemItem!!.name)
            }
        }
        gv_item.adapter = mAdapter
        gv_item.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 选中物品
            selectItem(position)
        }
        itemList[0].performClick()
        pb_use.setOnClickListener {
            useItem()
        }
    }

    private fun loadItems(): Int {
        val list = itemH.findAll(theIndex)
        mList.clear()
        mList.addAll(list)
        mAdapter.notifyDataSetChanged()
        return list.size
    }

    private fun selectItem(position: Int) {
        if(mList.size > 0) {
            mItem = mList[position]

            val theView = gv_item.getChildAt(theItem)
            theView.v_selection.clearAnimation()
            theView.v_selection.visibility = View.GONE
            theView.tv_name.setTextColor(resources.getColor(R.color.colorLabel))

            theItem = position
            val view = gv_item.getChildAt(position)
            view.v_selection.visibility = View.VISIBLE
            view.tv_name.setTextColor(resources.getColor(R.color.colorAccent))
            val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.selection)
            view.v_selection.startAnimation(anim)
            // 显示物品信息
            showItemInfo()
        } else {
            tv_item_text.text = getString(R.string.no_item)
            pb_use.visibility = View.GONE
        }
    }

    private fun showItemInfo() {
        tv_item_text.text = mItem.itemItem!!.text
    }

    private fun useItem() {

    }
}
