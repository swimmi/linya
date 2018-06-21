package net.swimmi.linya.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import net.swimmi.linya.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

class ActBattle : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        constraintLayout {
            relativeLayout {
                imageView {
                    id = R.id.iv_close
                    imageResource = R.drawable.ic_back
                    padding = dip(8)
                    onClick { finish() }
                }.lparams { margin = dip(4) }
                textView(R.string.battle) {
                    textSize = sp(7).toFloat()
                    textColorResource = R.color.colorAccent
                }.lparams { centerInParent() }
                backgroundResource = R.drawable.bg_panel
            }.lparams{
                width = matchParent
                height = wrapContent
            }
            backgroundResource = R.color.colorScene
            padding = dip(8)
            matchParent
        }

        initView()
    }

    private fun initView() {

    }

    override fun onClick(view: View) {
    }
}
