package dev.anmatolay.lirael.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
