package net.swimmi.linya.ui.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_partner.*
import kotlinx.android.synthetic.main.partner_info.*
import kotlinx.android.synthetic.main.partner_view.view.*
import kotlinx.android.synthetic.main.popup_partner_catalog.view.*
import kotlinx.android.synthetic.main.popup_partner_detail.view.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.data.DatConst
import net.swimmi.linya.data.helper.HPartner
import net.swimmi.linya.data.helper.HPlayer
import net.swimmi.linya.model.Partner
import net.swimmi.linya.ui.utils.UDisplay
import net.swimmi.linya.ui.view.combine.CatalogTab
import net.swimmi.linya.ui.view.combine.LingTab
import net.swimmi.linya.ui.view.combine.PartnerView
import net.swimmi.linya.ui.view.custom.PopupView

class ActPartner : AppCompatActivity(), View.OnClickListener {

    private var catalogPopupView: PopupView? = null
    private var detailPopupView: PopupView? = null

    private lateinit var mList: MutableList<Partner>
    private lateinit var mAdapter: AdpCommon<Partner>
    private lateinit var mThread: Thread

    private var partnerH: HPartner = HPartner()
    private var playerH: HPlayer = HPlayer()

    private var thePartner = 0 //当前选中伙伴
    private var theType = 0 //当前选中类型
    private var theLing = 0 //当前选择元灵
    private var theInfo = 0 //当前伙伴操作页，属性|技能|天赋...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)

        initView()
    }

    private fun initView() {
        mutableListOf(iv_close, tv_catalog).forEach {
            it.setOnClickListener(this)
        }
        initPartnerInfo()
        initPartnerPanel()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_close -> this.finish()
            R.id.tv_catalog ->  {
                mThread = Thread(Runnable {
                    kotlin.run {
                        val message = Message()
                        message.what = 0
                        mHandler.sendMessage(message)
                    }
                })
                mThread.start()
            }
        }
    }


    private var mHandler = @SuppressLint("HandlerLeak")
    object:Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> initPartnerCatalog()
            }
        }
    }

    private fun initPartnerInfo() {

        // 添加伙伴操作组TAB
        val infoList = mutableListOf(pt_role, pt_skill, pt_talent, pt_soul, pt_stone)
        for ( (index, tab) in infoList.withIndex()) {
            tab.setOnClickListener {
                infoList[theInfo].background = getDrawable(R.drawable.bg_tab)

                theInfo = index
                tab.background = getDrawable(R.drawable.bg_tab_on)
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1f
            params.setMargins(6, 12, 6, 0)
            tab.layoutParams = params
            tab.background = getDrawable(R.drawable.bg_tab)
        }
        infoList[0].performClick()
    }

    private fun initPartnerPanel() {
        val list = playerH.getPartners()
        val pvList = mutableListOf<PartnerView>()
        for ( (index, myPartner) in list.withIndex()) {
            val pv = PartnerView(this, null)
            pv.setValues(myPartner)
            pv.setOnClickListener {
                pvList[thePartner].v_selection.clearAnimation()
                pvList[thePartner].v_selection.visibility = View.GONE
                pvList[thePartner].tv_name.setTextColor(resources.getColor(R.color.colorWhite))

                thePartner = index
                pv.v_selection.visibility = View.VISIBLE
                pv.tv_name.setTextColor(resources.getColor(R.color.colorAccent))
                val anim = AnimationUtils.loadAnimation(this, R.anim.selection)
                pv.v_selection.startAnimation(anim)
                showPartnerInfo()
            }
            pvList.add(pv)
            ll_my_partner.addView(pv)
        }
        pvList[0].performClick()
    }

    private fun initPartnerCatalog() {

        theLing = 0
        theType = 0
        mList = mutableListOf()
        mAdapter = object:AdpCommon<Partner>(this, mList, R.layout.item_catalog_partner) {
            override fun convert(helper: ViewHolder, item: Partner, position: Int) {
                val partnerView = helper.getView<PartnerView>(R.id.pv_item)
                partnerView.setValues(item)
                if (item.isCollected) {
                    helper.setText(R.id.tv_text, item.text)
                    helper.setBackground(R.id.container, R.drawable.bg_catalog_partner)
                    helper.setBackground(R.id.ll_partner_bg, R.drawable.bg_partner_view)
                    helper.setText(R.id.tv_aptt, String.format(getString(R.string.fmt_partner_aptt), item.aptt))
                } else {
                    helper.setText(R.id.tv_text, getString(R.string.partner_lack))
                    helper.setBackground(R.id.container, R.drawable.bg_catalog_partner_lack)
                    helper.setBackground(R.id.ll_partner_bg, R.drawable.bg_partner_view_grey)
                }
            }
        }

        val catalogView = LayoutInflater.from(this).inflate(R.layout.popup_partner_catalog, null)
        catalogView.gv_partner.adapter = mAdapter
        catalogPopupView = PopupView.Builder(this)
                .setView(catalogView)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.AnimPopupView)
                .setOutsideTouchable(false)
                .create()
        catalogPopupView!!.tag = "first"
        val detailView = LayoutInflater.from(this).inflate(R.layout.popup_partner_detail, null)
        detailPopupView = PopupView.Builder(this)
                .setView(detailView)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.AnimPopupView)
                .setOutsideTouchable(false)
                .create()
        catalogView.gv_partner.setOnItemClickListener { adapterView, view, i, l ->
            run {
                showPartnerDetail()
            }
        }

        catalogView.iv_close_catalog.setOnClickListener { catalogPopupView!!.dismiss() }
        detailView.iv_close_detail.setOnClickListener { detailPopupView!!.dismiss() }

        // 添加类型筛选
        val catalogList: MutableList<CatalogTab> = mutableListOf()
        for( (index, str) in DatConst.PARTNER_TYPES.withIndex()) {
            val tab = CatalogTab(this, null)
            tab.name = str
            tab.background = getDrawable(R.drawable.bg_tab)
            tab.setIcon(DatConst.PARTNER_TYPE_ICONS[index])
            tab.setOnClickListener {
                catalogList[theType].background = getDrawable(R.drawable.bg_tab)
                catalogList[theType].setIcon(DatConst.PARTNER_TYPE_ICONS[theType])

                theType = index

                tab.background = getDrawable(R.drawable.bg_tab_on)
                tab.setIcon(DatConst.PARTNER_TYPE_ON_ICONS[index])

                catalogView.tv_partner_count.text = String.format(getString(R.string.fmt_partner_count), 0, loadPartnerAll())
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1f
            params.setMargins(6, 12, 6, 0)
            catalogList.add(tab)
            catalogView.ll_catalog_tab.addView(tab, params)
        }
        catalogList[0].performClick()

        // 添加五灵筛选TAB
        val lingList: MutableList<LingTab> = mutableListOf()
        for ( (index, _) in DatConst.PARTNER_LING_ICONS.withIndex()) {
            val tab = LingTab(this, null)
            tab.setLing(index)
            tab.setOnClickListener {
                lingList[theLing].background = null
                theLing = index
                tab.background = getDrawable(R.drawable.bg_tab_on)

                catalogView.tv_partner_count.text = String.format(getString(R.string.fmt_partner_count), 0, loadPartnerAll())
            }
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1f
            params.setMargins(6, 12, 6, 0)
            lingList.add(tab)
            catalogView.ll_catalog_tab_ling.addView(tab, params)
        }
        lingList[0].performClick()

        showPartnerCatalog()
    }

    private fun loadPartnerAll(): Int{
        //加载所有符合条件的Partner并返回计数
        val list = partnerH.findAll(theType, theLing)
        mList.clear()
        mList.addAll(list)
        mAdapter.notifyDataSetChanged()
        return list.size
    }

    private fun showPartnerInfo() {
        setPartnerStar(5)
    }

    private fun setPartnerStar(star: Int) {
        var starCount = star
        var resId = R.mipmap.partner_star
        if (starCount > 5) {
            starCount -= 5
            resId = R.mipmap.partner_star_purple
        }
        ll_star.removeAllViews()
        (1..starCount).forEach {
            val starIv = ImageView(this)
            val size = UDisplay(this).d2p(16f)
            val layoutParams = LinearLayout.LayoutParams(size, size)
            layoutParams.setMargins(4,0,4,0)
            starIv.layoutParams = layoutParams
            starIv.setImageResource(resId)
            ll_star.addView(starIv)
        }
    }

    private fun showPartnerCatalog() {
        catalogPopupView!!.setBackgroundDarkLevel(0.1f)
        catalogPopupView!!.showAtLocation(container, Gravity.CENTER, 0, 0)
    }

    private fun showPartnerDetail() {
        detailPopupView!!.showAtLocation(container, Gravity.CENTER, 0, 0)
    }

    override fun onBackPressed() {
        when {
            detailPopupView != null && detailPopupView!!.isShowing -> detailPopupView!!.dismiss()
            catalogPopupView != null && catalogPopupView!!.isShowing -> catalogPopupView!!.dismiss()
            else -> super.onBackPressed()
        }
    }
}
