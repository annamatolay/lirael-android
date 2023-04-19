package dev.anmatolay.lirael.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentSplashBinding
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashEvent>() {

    override val viewModel by viewModel<SplashViewModel>()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentSplashBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBarAndBottomNavigationVisibilityToGone()

        viewModel.uiState.observe { state ->
            if (!state.isIdle) {
                binding.layoutSplash.jumpToState(R.id.end)
                if (state.isUserExist)
                    navigateTo(SplashFragmentDirections.actionToStatisticsFragment())
                else
                    navigateTo(SplashFragmentDirections.actionToWelcomeFragment())
            }
        }

        binding.layoutSplash.run {
            transitionToState(R.id.middle)
            setTransitionDuration(1000)
            setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    // Optimised time you can wait to start ending animation
                    // Otherwise motion animation will conflict with navigation transition animation
                    Thread.sleep(1440)
                    binding.layoutSplash.transitionToState(R.id.end)

                }

                override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {

                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {

                }
            })
        }
    }

    override fun onDestroyView() {
        requireActivity().findViewById<AppBarLayout>(R.id.app_bar).visibility = VISIBLE
        // Disable toolbar actions during onboarding
        activity?.findViewById<View>(R.id.settings_item)?.isVisible = false
        activity?.findViewById<View>(R.id.ui_mode_item)?.isVisible = false
        super.onDestroyView()
    }

    private fun setAppBarAndBottomNavigationVisibilityToGone() {
        requireActivity().findViewById<AppBarLayout>(R.id.app_bar).visibility = GONE
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = GONE
    }
}
