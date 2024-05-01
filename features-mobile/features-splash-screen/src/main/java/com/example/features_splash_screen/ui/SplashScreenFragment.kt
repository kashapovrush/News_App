package com.example.features_splash_screen.ui

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.features_common.constans.Constants.EXTRA_CURRENT_TIME
import com.example.features_common.viewmodel.SourcesViewModel
import com.example.features_splash_screen.R
import com.example.features_splash_screen.di.SplashScreenComponentProvider
import com.example.prefrences.PreferencesManager
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenFragment : Fragment() {

    private lateinit var viewModel: SourcesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onAttach(context: Context) {
        (requireActivity().application as SplashScreenComponentProvider).getSplashScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[SourcesViewModel::class.java]

        val time = preferencesManager.getLong(EXTRA_CURRENT_TIME)

        if (time != 0L && System.currentTimeMillis() - time > 60000) {
            lifecycleScope.launch {
                viewModel.clearCached()
            }
        }
    }

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