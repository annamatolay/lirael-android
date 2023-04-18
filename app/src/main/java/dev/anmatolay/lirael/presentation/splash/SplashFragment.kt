package dev.anmatolay.lirael.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
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

        setAppBarAndBottomNavigationVisibility(GONE)

        viewModel.uiState.observe { state ->
            if (!state.isIdle) {
                binding.layoutSplash.jumpToState(R.id.end)
                navigateTo(SplashFragmentDirections.actionToStatisticsFragment())
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
        setAppBarAndBottomNavigationVisibility(VISIBLE)
        super.onDestroyView()
    }

    private fun setAppBarAndBottomNavigationVisibility(visibility: Int) {
        requireActivity().findViewById<AppBarLayout>(R.id.app_bar).visibility = visibility
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = visibility
    }
}
