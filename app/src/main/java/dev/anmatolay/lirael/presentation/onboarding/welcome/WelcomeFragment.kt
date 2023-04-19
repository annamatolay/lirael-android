package dev.anmatolay.lirael.presentation.onboarding.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingWelcomeBinding
import dev.anmatolay.lirael.presentation.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<WelcomeViewModel>()
    private lateinit var binding: FragmentOnboardingWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardingWelcomeBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
