package com.example.nekodiary.ui.fx

import android.animation.Animator
import android.content.Context
import android.view.View

object ViewShaker {

    private const val delta_def = 10f
    private var delta: Float = delta_def
    private var position: Float = 0f
    private var isShaking: Boolean = false
    private var firstTimePosition = true

    fun shake(view: View) {
        shake(view, delta_def)
    }

    fun shake(view: View, delta: Float) {

        if(isShaking)
            return

        this.delta = delta
        if(firstTimePosition) {
            position = view.x
            firstTimePosition = false
        }
        isShaking = true;

        view.animate().apply {
            translationX(ViewShaker.delta)

            setListener(object : Animator.AnimatorListener {

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {

                    if (isShaking) {
                        val lt = this
                        ViewShaker.delta *= -1
                        view.animate().apply {
                            translationX(ViewShaker.delta)
                            setListener(lt)
                            start()
                        }
                    }else{
                        view.x = position
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })

            duration = 50
            start()
        }
    }

    fun stop() {
        if (isShaking) {
            isShaking = false
        }
    }

}