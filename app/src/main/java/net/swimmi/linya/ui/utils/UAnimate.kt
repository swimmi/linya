package net.swimmi.linya.ui.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.*
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

        fun round(from: View, to: View) {
            val animatorSet = AnimatorSet()
            animatorSet.duration = 1000
            animatorSet.interpolator = AccelerateInterpolator()
            val oa1 = ObjectAnimator.ofFloat(from, "translationX", 0f, to.x - from.x)
            val oa2 = ObjectAnimator.ofFloat(from, "translationY", 0f, to.y - from.y)
            animatorSet.playTogether(oa1, oa2)
            animatorSet.start()
            var flag = true
            animatorSet.addListener(object:Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    if(flag) {
                        shock(to)
                        animatorSet.reverse()
                        flag = false
                    }
                }
            })
        }

        fun shock(target: View) {
            val animatorSet = AnimatorSet()
            animatorSet.duration = 300
            animatorSet.interpolator = OvershootInterpolator()
            val oa1 = ObjectAnimator.ofFloat(target, "translationY", 0f, 10f)
            val oa2 = ObjectAnimator.ofFloat(target, "translationY", 0f, -10f)
            animatorSet.play(oa1).after(oa2)
            animatorSet.start()
        }
    }
}