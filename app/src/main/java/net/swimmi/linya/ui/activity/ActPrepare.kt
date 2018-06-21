package net.swimmi.linya.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_prepare.*
import net.swimmi.linya.R

class ActPrepare : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare)

        initView()
    }

    private fun initView() {

        mutableListOf(iv_close, iv_set_array, pb_enter_battle).forEach {
            it.setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_close -> this.finish()
            R.id.iv_set_array -> {
                val intent = Intent()
                intent.setClass(this, ActArray::class.java)
                startActivity(intent)
            }
            R.id.pb_enter_battle -> {
                val intent = Intent()
                intent.setClass(this, ActBattle::class.java)
                startActivity(intent)
            }
        }
    }
}
