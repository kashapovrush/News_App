package com.example.features_splash_screen

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.airbnb.lottie.LottieAnimationView

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        val animationView = view.findViewById<LottieAnimationView>(R.id.animationView)
        animationView.setAnimation("splash_screen.json")
        animationView.playAnimation()

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                requireActivity().supportFragmentManager.setFragmentResult(
                    ANIMATION_STATE,
                    bundleOf(EXTRA_DATA to ANIMATION_FINISHED)
                )
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })


        return view
    }

    companion object {

        const val ANIMATION_FINISHED = 2
        const val ANIMATION_CONTINUES = 1
        const val ANIMATION_STATE = "animation_state"
        const val EXTRA_DATA = "data"

        fun newInstance() = SplashScreenFragment()
    }
}