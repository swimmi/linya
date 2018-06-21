package net.swimmi.linya.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_adventure.*
import kotlinx.android.synthetic.main.activity_adventure.view.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.model.AdvBlock
import net.swimmi.linya.ui.utils.UDisplay

class ActAdventure : AppCompatActivity(), View.OnClickListener {

    private lateinit var mList: MutableList<AdvBlock>
    private lateinit var mAdapter: AdpCommon<AdvBlock>

    private val blockCols = 7
    private val blockCount = 84

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adventure)

        initView()
        loadBlocks()
    }

    private fun initView() {
        mutableListOf(iv_close).forEach {
            it.setOnClickListener(this)
        }

        mList = mutableListOf()
        mAdapter = object: AdpCommon<AdvBlock>(this, mList, R.layout.item_adv_block) {
            override fun convert(helper: ViewHolder, item: AdvBlock, position: Int) {

            }

        }
        val layoutParams = RelativeLayout.LayoutParams(UDisplay(this).d2p((blockCols * 50).toFloat()),
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        gv_block.layoutParams = layoutParams
        gv_block.adapter = mAdapter
        gv_block.numColumns = blockCols
    }

    private fun loadBlocks() {
        for (i in 1..blockCount) {
            val block = AdvBlock(1, i - 1)
            mList.add(block)
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_close -> this.finish()
        }
    }
}
