package dev.anmatolay.lirael.presentation.onboarding.diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingDietBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DietFragment : BaseFragment<Event>() {

    override val viewModel by viewModel<CustomViewModel>()
    private lateinit var binding: FragmentOnboardingDietBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardingDietBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
}
