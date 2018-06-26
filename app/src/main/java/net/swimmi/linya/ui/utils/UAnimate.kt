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

        val UP = 0
        val DOWN = 1

        fun mirrorRotate(context: Context, view: View, after: () -> Unit) {
            var anim = AnimationUtils.loadAnimation(context, R.anim.mirror_rotate_out)
            anim.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationRepeat(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    after()
                    anim = AnimationUtils.loadAnimation(context, R.anim.mirror_rotate_in)
                    view.startAnimation(anim)
                }

                override fun onAnimationStart(p0: Animation?) {
                }

            })
            view.startAnimation(anim)
        }

        fun round(from: View, to: View, after: () -> Unit) {
            val animatorSet = AnimatorSet()
            animatorSet.duration = 600
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
                        after()
                        animatorSet.reverse()
                        flag = false
                    }
                }
            })
        }

        fun shock(target: View, direction: Int) {
            val animatorSet = AnimatorSet()
            animatorSet.duration = 300
            animatorSet.interpolator = OvershootInterpolator()
            val oa1 = ObjectAnimator.ofFloat(target, "translationY", 0f, 10f)
            val oa2 = ObjectAnimator.ofFloat(target, "translationY", 0f, -10f)
            when (direction) {
                UP -> animatorSet.play(oa1).after(oa2)
                DOWN -> animatorSet.play(oa2).after(oa1)
            }
            animatorSet.start()
        }
    }
}