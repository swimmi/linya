package net.swimmi.linya.ui.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_random.*
import kotlinx.android.synthetic.main.random_button.view.*
import net.swimmi.linya.R
import net.swimmi.linya.adapter.AdpCommon
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.base.ViewHolder
import net.swimmi.linya.data.DatConst
import net.swimmi.linya.model.Stone
import net.swimmi.linya.ui.utils.URandom
import net.swimmi.linya.ui.view.combine.RandomButton
import org.jetbrains.anko.toast
import java.util.*
import kotlin.concurrent.timerTask

class ActRandom : ActBase(), View.OnClickListener {

    private lateinit var mList: MutableList<Stone>
    private lateinit var mAdapter: AdpCommon<Stone>

    private val random = URandom()
    private var theNumber = 0
    private var speed: Long = 800

    private lateinit var rbList: MutableList<RandomButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)

        initView()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_close -> this.finish()
            R.id.pb_auto -> autoRandom()
            R.id.pb_sell -> sellBadStones()
        }
    }

    private fun initView() {
        container.setOnTouchListener(this)
        mutableListOf(iv_close, pb_sell, pb_auto).forEach {
            it.setOnClickListener(this)
        }

        mList = mutableListOf()
        mAdapter = object: AdpCommon<Stone>(this, mList, R.layout.item_stone) {
            override fun convert(helper: ViewHolder, item: Stone, position: Int) {
                helper.setText(R.id.tv_name, item.name)
                if (item.quality == 0)
                    helper.setText(R.id.tv_name, R.string.bad_stone)
                if (item.quality == 5) {
                    helper.setText(R.id.tv_name, R.string.stone_piece)
                    item.quality = 4
                }
                helper.setBackground(R.id.tv_name, DatConst.RANDOM_COLOURS[item.quality])
            }
        }
        gv_stone.adapter = mAdapter
        rbList = mutableListOf(rb_first, rb_second, rb_third, rb_fourth, rb_fifth)
        for( (index, rb) in rbList.withIndex()) {
            rb.setOnClickListener {

                mList.add(randomStone(theNumber))
                mAdapter.notifyDataSetChanged()
                gv_stone.smoothScrollToPosition (mList.size)
                rb.sv_selection.stopAnimation()
                rb.sv_selection.visibility = View.GONE
                theNumber = nextNumber(index)
                setNumber()
            }
        }
        setNumber()
    }

    private fun setNumber() {
        rbList.forEach {
            it.isEnabled = false
        }
        rbList[theNumber].isEnabled = true
        rbList[theNumber].sv_selection.visibility = View.VISIBLE
        rbList[theNumber].sv_selection.startAnimation(speed)
    }

    private fun nextNumber(number: Int): Int {
        var next = number
        val rate = DatConst.RANDOM_RATES[number]
        if(random.dice(rate)) next ++ else next = 0
        if (next == 5) next = 0
        return next
    }

    private fun randomStone(number: Int): Stone {
        val rateArray = DatConst.STONE_RATES[number]
        val length = DatConst.STONE_TYPES.size
        val index = Random().nextInt(length)
        val quality = random.dice(rateArray)
        Log.i("TAG>>>", index.toString() + ": " + quality)
        return Stone(DatConst.STONE_TYPES[index], quality)
    }

    private fun autoRandom() {
        speed = 300

        var count = 80
        val timer = Timer()
        val timerTask = timerTask {
            kotlin.run {
                val message = Message()
                message.what = 0
                mHandler.sendMessage(message)
            }
            count --
            if (count == 0) {
                pb_auto.isEnabled = true
                speed = 800
                timer.cancel()
            }
        }
        timer.schedule(timerTask, 300, 300)
        pb_auto.isEnabled = false
    }

    private var mHandler = @SuppressLint("HandlerLeak")
    object:Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> rbList[theNumber].performClick()
            }
        }
    }

    private fun sellBadStones() {
        val count = mList.count { it.quality == 0 }
        toast(String.format(getString(R.string.fmt_sell_bad_stones), count))
        mList.removeAll { it.quality == 0 }
        mList.sortBy { it.quality }
        mAdapter.notifyDataSetChanged()
    }
}
