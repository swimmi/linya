package net.swimmi.linya.base

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import net.swimmi.linya.R
import net.swimmi.linya.ui.utils.UDisplay
import net.swimmi.linya.ui.view.custom.RippleView
import java.util.*

abstract class ActBase : AppCompatActivity(), View.OnTouchListener {

    private lateinit var ripple: RippleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ripple = LayoutInflater.from(applicationContext).inflate(R.layout.ripple_view, null) as RippleView
        (window.decorView as ViewGroup).addView(ripple)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val size = UDisplay(applicationContext).d2p(48f)
                val params = FrameLayout.LayoutParams(size, size)
                params.setMargins(event.x.toInt() - size / 2, event.y.toInt() - size / 2, 0, 0)
                ripple.layoutParams = params
                ripple.startAnimation()
            }
        }
        return false
    }
}