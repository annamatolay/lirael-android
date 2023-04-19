package dev.anmatolay.lirael.presentation.onboarding.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingNameBinding
import dev.anmatolay.lirael.presentation.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class NameFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<NameViewModel>()
    private lateinit var binding: FragmentOnboardingNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardingNameBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
