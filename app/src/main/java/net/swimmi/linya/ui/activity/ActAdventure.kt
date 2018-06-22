package net.swimmi.linya.ui.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_adventure.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.model.AdvBlock
import net.swimmi.linya.model.Adventure
import net.swimmi.linya.model.Game
import net.swimmi.linya.ui.utils.UAnimate
import net.swimmi.linya.ui.utils.UDisplay
import net.swimmi.linya.ui.utils.hide
import net.swimmi.linya.ui.utils.random
import org.jetbrains.anko.backgroundResource

class ActAdventure : ActBase(), View.OnClickListener {

    private lateinit var mList: MutableList<AdvBlock>
    private lateinit var mAdapter: AdpCommon<AdvBlock>

    private val blockCols = 7
    private val blockCount = 84

    private lateinit var mAdventure: Adventure
    private var mPass = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        initView()
        loadAdventure()
        loadBlocks()
    }

    /**
     * 加载奇遇地图信息
     */
    private fun loadAdventure() {
        Game.advList.random()
        mAdventure = Game.advList.random()
        tv_name.text = mAdventure.name
        tv_text.text = mAdventure.text
    }

    private fun initView() {
        container.setOnTouchListener(this)
        mutableListOf(container, iv_close).forEach {
            it.setOnClickListener(this)
        }

        mList = mutableListOf()
        mAdapter = object: AdpCommon<AdvBlock>(this, mList, R.layout.item_adv_block) {
            override fun convert(helper: ViewHolder, item: AdvBlock, position: Int) {
                helper.setBackground(R.id.container, AdvBlock.StatusBg[item.status])
            }
        }
        val layoutParams = RelativeLayout.LayoutParams(UDisplay(this).d2p((blockCols * 50).toFloat()),
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        gv_block.layoutParams = layoutParams
        gv_block.adapter = mAdapter
        gv_block.numColumns = blockCols
        gv_block.setOnItemClickListener { _, view, position, _ ->
            run {
                val block = mList[position]
                val plot = block.plot
                when (block.status) {
                    AdvBlock.CanIn -> {
                        // 翻转Block
                        UAnimate.mirrorRotate(this, view) { view.backgroundResource = R.drawable.bg_adv_block_in }
                        block.status = AdvBlock.In
                    }
                    AdvBlock.In -> {
                        block.status = AdvBlock.Off
                        // 设置四周可通过Block
                        setBlockCanIn(position)
                        if (plot != null) {
                            tv_plot_npc.text = plot.npc.toString()
                            tv_plot_text.text = plot.text
                            ll_plot.visibility = View.VISIBLE
                        }
                    }
                    AdvBlock.Off -> {
                        return@setOnItemClickListener
                    }
                }
                if (plot != null) {
                    tv_plot_npc.text = plot.npc.toString()
                    tv_plot_text.text = plot.text
                    ll_plot.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * 加载地图所有Block
     * 先随机放置任务事件Block
     * 再填充其余位置
     */
    private fun loadBlocks() {
        val pass = mAdventure.pass[mPass]
        val plots = pass.plots
        for (i in 0 until plots.size) {
            val block = AdvBlock(2, i, plots[i])
            mList.add(block)
        }
        for (i in plots.size until blockCount) {
            val block = AdvBlock(1, i, null)
            if (i == blockCount / 2)
                block.status = AdvBlock.CanIn
            mList.add(block)
        }
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置已进入Block的四周为可进入状态
     * [position] 已进入Block的位置
     */
    private fun setBlockCanIn(position: Int) {
        val sideArray = arrayOf(position - blockCols, position + 1, position + blockCols, position -1)
        for ((index, side) in sideArray.withIndex()) {
            if (side !in 0 until blockCount)
                continue
            if (index == 1 || index == 3) {
                if (side / blockCols != position / blockCols)
                    continue
            }
            val block = mList[side]
            if (block.status == AdvBlock.ON)
                block.status = AdvBlock.CanIn
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_close -> this.finish()
            R.id.container -> {
                ll_plot.hide()
            }
        }
    }
}