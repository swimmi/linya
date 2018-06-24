package net.swimmi.linya.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_battle.*
import net.swimmi.linya.R
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.model.Battle
import net.swimmi.linya.model.Character
import net.swimmi.linya.model.Team
import net.swimmi.linya.ui.view.combine.CharacterView
import org.jetbrains.anko.backgroundResource
import java.util.*
import kotlin.concurrent.timerTask

class ActBattle : ActBase(), View.OnClickListener {

    private val tag = "ActBattle"
    private val arraySize = 9

    private lateinit var mBattle: Battle
    private lateinit var mTeamA: Team
    private lateinit var mTeamB: Team

    private var mTime = 0
    private var mTimer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        initView()
        prepare()
        start()
    }

    private fun initView() {
        mutableListOf(iv_close).forEach {
            it.setOnClickListener(this)
        }
    }

    private fun loadBattle() {
        val arrayType = 1
        val char1 = Character(0, 70, 14)
        val char2 = Character(0, 70, 13)
        val char3 = Character(0, 70, 12)
        val char4 = Character(0, 70, 15)
        val char5 = Character(0, 70, 16)
        val charB = Character(0, 65, 12)
        val membersA = listOf(char1, char2, char3, char4, char5)
        val membersB = listOf(charB)
        mTeamA = Team(membersA, arrayType)
        mTeamB = Team(membersB, arrayType)
        mBattle = Battle(mTeamA, mTeamB, 180)
    }

    /**
     * 准备战斗
     */
    private fun prepare() {
        loadBattle()
        mTime = mBattle.duration
        val array = Team.getArray(mTeamA.arrayType)

        for ((index, a) in array.withIndex()) {
            if (a != 0) {
                val cv = CharacterView(this)
                val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(200 + 200 * (index%3),1000 + 200 * (index/3),0,0)
                cv.layoutParams = params
                val member = mTeamA.members[a - 1]
                member.view = cv
                cv.setOnClickListener {
                    member.goto(mTeamB.members[0])
                }
                rl_battle.addView(cv)
            }
        }

        for (member in mTeamB.members) {
            val cv = CharacterView(this)
            val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(600,200,0,0)
            cv.layoutParams = params
            member.view = cv
            rl_battle.addView(cv)
        }

    }

    /**
     * 进入战斗
     */
    private fun start() {
        timerTick()
    }

    /**
     * 计时
     */
    private fun timerTick() {
        val timerTask = timerTask {
            kotlin.run {
                val message = Message()
                message.what = 0
                mHandler.sendMessage(message)
            }
        }
        mTimer = Timer()
        mTimer.schedule(timerTask, 0, 1000)
    }

    private var mHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                0 -> setTime()
            }
        }
    }

    private fun setTime() {
        if (mTime >= 0) {
            tv_timer.text = String.format(getString(R.string.fmt_battle_timer, mTime / 60, mTime % 60))
            mTime --
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_close -> finish()
        }
    }
}
