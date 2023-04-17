package dev.anmatolay.lirael.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import com.google.android.material.transition.MaterialFadeThrough
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : BaseFragment<StatisticsEvent>() {

    override val viewModel by viewModel<StatisticsViewModel>()
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentStatisticsBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.run {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.uiState.observe { state ->
            binding.text.isVisible = state.isTextVisible
        }

        binding.layout.setOnClickListener {
            triggerEvent(StatisticsEvent.ScreenOnClicked)
        }
    }
}
