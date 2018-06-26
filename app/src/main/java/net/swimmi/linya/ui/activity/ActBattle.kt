package net.swimmi.linya.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_battle.*
import net.swimmi.linya.R
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.model.Battle
import net.swimmi.linya.model.Character
import net.swimmi.linya.model.Team
import net.swimmi.linya.ui.utils.UDisplay
import net.swimmi.linya.ui.utils.joinWith
import net.swimmi.linya.ui.view.custom.CharacterView
import org.jetbrains.anko.dip
import java.util.*
import kotlin.concurrent.timerTask

class ActBattle : ActBase(), View.OnClickListener {

    private val tag = "ActBattle"

    private lateinit var mBattle: Battle

    private var mTime = 0
    private var mTimer = Timer()
    private var mIndex = 0
    private lateinit var mQueue: List<Character>

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
        val a1 = Character(0, 70, 14)
        val a2 = Character(1, 70, 13)
        val a3 = Character(0, 70, 12)
        val a4 = Character(1, 70, 15)
        val a5 = Character(0, 70, 16)
        val membersA = listOf(a1, a2, a3, a4, a5)
        val mTeamA = Team(membersA, 1)

        val b1 = Character(1, 65, 12)
        val b2 = Character(0, 65, 12)
        val b3 = Character(1, 65, 12)
        val b4 = Character(0, 65, 12)
        val b5 = Character(0, 65, 12)
        val membersB = listOf(b1, b2, b3, b4, b5)
        val mTeamB = Team(membersB, 0)

        mBattle = Battle(mTeamA, mTeamB, 180)
    }

    /**
     * 准备战斗
     */
    private fun prepare() {
        loadBattle()
        arrayTeam()
        mTime = mBattle.duration

    }

    private fun arrayTeam() {

        val parentWidth = UDisplay(this).getScreenWidth() - dip(16)
        val distanceWidth = dip(80)
        val distanceHeight = dip(90)

        var team = mBattle.teamA
        var array = Team.getArray(team.arrayType)
        for ((index, a) in array.withIndex()) {
            if (a != 0) {
                val member = team.members[a - 1]
                val cv = CharacterView(this, member)
                val params = RelativeLayout.LayoutParams(cv.size, cv.size)
                params.addRule(RelativeLayout.BELOW, R.id.v_divider)

                params.setMargins((parentWidth / 2 - cv.size / 2) + distanceWidth * (index%3 - 1),dip(32) + distanceHeight * (index/3),0,0)
                cv.layoutParams = params
                member.mView = cv
                battle_field.addView(cv)
            }
        }

        team = mBattle.teamB
        array = Team.getArray(team.arrayType)
        for ((index, a) in array.withIndex()) {
            if (a != 0) {
                val member = team.members[a - 1]
                member.isNpc = true
                val cv = CharacterView(this, member)
                val params = RelativeLayout.LayoutParams(cv.size, cv.size)
                params.addRule(RelativeLayout.ABOVE, R.id.v_divider)

                params.setMargins((parentWidth / 2 - cv.size / 2) + distanceWidth * (index%3 - 1),0,0,dip(32) + distanceHeight * (index/3))
                cv.layoutParams = params
                member.mView = cv
                battle_field.addView(cv)
            }
        }
    }

    /**
     * 进入战斗
     */
    private fun start() {
        // 战斗队列
        mQueue = mBattle.teamA.members.joinWith(mBattle.teamB.members)
        timerTick()
    }

    private fun roundAttack() {
        if (!mBattle.isOver) {
            val self = getSelf()
            val target = getTarget(self)

            if (target == null) {
                mBattle.isOver = true
                return
            }

            when (self.type) {
                0 -> self.hit(target!!)
                1 -> self.shoot(battle_field, target!!)
            }
            mIndex++
            if (mIndex == mQueue.size)
                mIndex = 0
        }
    }

    private fun getSelf(): Character {
        var self = mQueue[mIndex]
        while (!self.isAlive) {
            mIndex ++
            self = mQueue[mIndex]
        }
        return self
    }

    private fun getTarget(character: Character): Character? {
        val team = getTargetTeam(character)
        for (cm in team.members) {
            if (cm.isAlive)
                return cm
        }
        return null
    }

    private fun getTargetTeam(character: Character): Team {
        var team = mBattle.teamB
        if (character.isNpc)
            team = mBattle.teamA
        return team
    }

    /**
     * 计时
     */
    private fun timerTick() {
        val timerTask = timerTask {
            kotlin.run {
                val msg = Message()
                msg.what = 0
                mHandler.sendMessage(msg)
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
        // 自动普攻
        if (mTime % 2 == 0)
            roundAttack()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_close -> finish()
        }
    }
}
