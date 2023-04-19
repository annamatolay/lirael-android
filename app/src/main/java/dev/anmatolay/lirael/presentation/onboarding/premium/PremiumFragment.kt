package dev.anmatolay.lirael.presentation.onboarding.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingPremiumBinding
import dev.anmatolay.lirael.presentation.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class PremiumFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<PremiumViewModel>()
    private lateinit var binding: FragmentOnboardingPremiumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardingPremiumBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
