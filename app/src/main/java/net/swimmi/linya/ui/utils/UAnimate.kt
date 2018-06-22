package net.swimmi.linya.ui.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import net.swimmi.linya.R

class UAnimate {

    companion object {

        fun mirrorRotate(context: Context, view: View, doSomething: () -> Unit) {
            var anim = AnimationUtils.loadAnimation(context, R.anim.mirror_rotate_out)
            anim.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    doSomething()
                    anim = AnimationUtils.loadAnimation(context, R.anim.mirror_rotate_in)
                    view.startAnimation(anim)
                }

                override fun onAnimationStart(p0: Animation?) {
                }

            })
            view.startAnimation(anim)
        }
    }
}