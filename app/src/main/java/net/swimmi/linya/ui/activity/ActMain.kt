package net.swimmi.linya.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import net.swimmi.linya.R
import net.swimmi.linya.base.ActBase
import net.swimmi.linya.data.DatGame

class ActMain : ActBase(), View.OnClickListener {

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("data", Context.MODE_PRIVATE)

        initView()
        initData()
    }

    private fun initView() {

        container.setOnTouchListener(this)
        mutableListOf(mb_challenge, mb_foster, mb_partner, mb_backpack, mb_battle, mb_adventure, mb_random)
                .forEach { it.setOnClickListener(this) }
    }

    private fun initData() {
        if(!sp.getBoolean("isLoaded", false)) {
            DatGame().addData(this)
            sp.edit().putBoolean("isLoaded", true).apply()
        }
    }

    override fun onClick(view: View) {
        val intent = Intent()
        when(view.id) {
            R.id.mb_challenge -> {
                toggleView(cl_challenge_action)
            }
            R.id.mb_foster -> {
                toggleView(cl_foster_action)
            }
            R.id.mb_partner -> {
                intent.setClass(this, ActPartner::class.java)
            }
            R.id.mb_backpack -> {
                intent.setClass(this, ActPack::class.java)
            }
            R.id.mb_battle -> {
                intent.setClass(this, ActPrepare::class.java)
                mb_challenge.performClick()
            }
            R.id.mb_adventure -> {
                intent.setClass(this, ActAdventure::class.java)
                mb_challenge.performClick()
            }
            R.id.mb_random -> {
                intent.setClass(this, ActRandom::class.java)
                mb_foster.performClick()
            }
        }
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intent)
    }

    private fun toggleView(view: View) {
        if (view.visibility == View.GONE) {
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale_zoom_in)
            view.visibility = View.VISIBLE
            view.startAnimation(animation)
        } else {
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale_fade_out)
            view.visibility = View.GONE
            view.startAnimation(animation)
        }
    }
}
