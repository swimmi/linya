package net.swimmi.linya.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_array.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.data.DatConst
import net.swimmi.linya.data.helper.HPartner
import net.swimmi.linya.data.helper.HPlayer
import net.swimmi.linya.model.MyPartner
import net.swimmi.linya.ui.view.combine.CatalogTab
import net.swimmi.linya.ui.view.combine.PartnerView

class ActArray : ActBase(), View.OnClickListener {

    private var playerH: HPlayer = HPlayer()
    private lateinit var mList: MutableList<MyPartner>
    private lateinit var mAdapter: AdpCommon<MyPartner>

    private var theType = 0 //当前选中类型

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array)

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
        // 加载伙伴目录
        initPartnerCatalog()
    }

    private fun initPartnerCatalog() {
        mList = mutableListOf()
        mAdapter = object:AdpCommon<MyPartner>(this, mList, R.layout.item_array_partner) {
            override fun convert(helper: ViewHolder, item: MyPartner, position: Int) {
                val partnerView = helper.getView<PartnerView>(R.id.pv_item)
                partnerView.setValues(item)
            }
        }
        gv_partner.adapter = mAdapter

        // 添加类型筛选
        val catalogList: MutableList<CatalogTab> = mutableListOf()
        for( (index, str) in DatConst.PARTNER_TYPES.withIndex()) {
            val tab = CatalogTab(this, null)
            tab.background = getDrawable(R.drawable.bg_tab)
            tab.setIcon(DatConst.PARTNER_TYPE_ICONS[index])
            tab.onlyIcon = true
            tab.setOnClickListener {
                catalogList[theType].background = getDrawable(R.drawable.bg_tab)
                catalogList[theType].setIcon(DatConst.PARTNER_TYPE_ICONS[theType])

                theType = index

                tab.background = getDrawable(R.drawable.bg_tab_on)
                tab.setIcon(DatConst.PARTNER_TYPE_ON_ICONS[index])
                loadPartnerAll()
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1f
            params.setMargins(6, 12, 6, 0)
            catalogList.add(tab)
            ll_catalog_tab.addView(tab, params)
        }
        catalogList[0].performClick()
    }

    private fun loadPartnerAll(){
        //加载所有符合条件的Partner
        val list = playerH.getAllPartner(theType)
        mList.clear()
        mList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }
}
