package net.swimmi.linya.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_adventure.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.model.AdvBlock
import net.swimmi.linya.model.Adventure
import net.swimmi.linya.model.Game
import net.swimmi.linya.ui.utils.*
import java.util.*

class ActAdventure : ActBase(), View.OnClickListener {

    private val tag = "ActAdventure"
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
        if (!USp.contains(this, tag)) {
            mAdventure = Game.advList.random()
            USp.saveObject(this, mAdventure, tag)
        } else
            mAdventure = USp.loadObject<Adventure>(this, tag)!!
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
                when (item.status) {
                    AdvBlock.In -> {
                        helper.setBackground(R.id.container, AdvBlock.TypeBg[item.type])
                        helper.setText(R.id.tv_name, AdvBlock.BlockNames[item.type])
                    }
                    AdvBlock.Off -> {
                        helper.setText(R.id.tv_name, "")
                    }
                }
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
                Log.d(tag, block.toString())
                when (block.status) {
                    AdvBlock.CanIn -> {
                        block.status = AdvBlock.In
                        if (block.type == AdvBlock.EMPTY) {
                            block.status = AdvBlock.Off
                            setBlockCanIn(position)
                        }
                        // 翻转Block
                        UAnimate.mirrorRotate(this, view) {
                            // 设置Block
                            mAdapter.notifyDataSetChanged()
                        }
                    }
                    AdvBlock.In -> {
                        if (block.type == AdvBlock.HINDER)
                            return@setOnItemClickListener
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
            }
        }
    }

    /**
     * 加载地图所有Block
     * >>先放置任务事件Block
     * >>再填充其余位置
     * >>最后打乱列表
     */
    private fun loadBlocks() {

        if (USp.contains(this, AdvBlock.tag)) {
            mList.addAll(USp.loadObject<MutableList<AdvBlock>>(this, AdvBlock.tag)!!)
        } else {
            // 加载任务Block
            val pass = mAdventure.pass[mPass]
            val plots = pass.plots
            for (i in 0 until plots.size) {
                val block = AdvBlock(AdvBlock.PLOT, 0, plots[i])
                mList.add(block)
            }
            // 加载唯一Block Start
            mList.add(AdvBlock(AdvBlock.EMPTY, status = AdvBlock.CanIn))
            // 加载唯一Block Buff
            mList.add(AdvBlock(AdvBlock.BUFF))
            // 加载唯一Block Dice
            mList.add(AdvBlock(AdvBlock.DICE))
            // 随机加载其余类型的Block
            for (i in 0..10) {
                val block = AdvBlock(AdvBlock.BATTLE)
                mList.add(block)
            }
            for (i in 0..10) {
                val block = AdvBlock(AdvBlock.HINDER)
                mList.add(block)
            }
            for (i in 0..20) {
                val block = AdvBlock(AdvBlock.CHEST)
                mList.add(block)
            }
            for (i in mList.size until blockCount) {
                val block = AdvBlock(AdvBlock.EMPTY)
                mList.add(block)
            }
            // 打乱Block顺序
            Collections.shuffle(mList)
            // 设置Position
            mList.withIndex().forEach { (index, it) -> it.position = index }
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
            if (block.status == AdvBlock.On)
                block.status = AdvBlock.CanIn
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_close -> this.finish()
            R.id.container -> {
                ll_plot.hide()
            }
        }
    }

    override fun onBackPressed() {
        USp.saveObject(this, mList, AdvBlock.tag)
        super.onBackPressed()
    }

    override fun finish() {
        USp.saveObject(this, mList, AdvBlock.tag)
        super.finish()
    }
}