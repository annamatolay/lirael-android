package dev.anmatolay.lirael.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
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

        setAppBarAndBottomNavigationVisibility(INVISIBLE)

        viewModel.uiState.observe { state ->
            if (!state.isIdle) {
                navigateTo(SplashFragmentDirections.actionToStatisticsFragment())
            }
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
