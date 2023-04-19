package dev.anmatolay.lirael.presentation.onboarding.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.dispose
import coil.load
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingPremiumBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.onboarding.diet.DietEvent
import dev.anmatolay.lirael.presentation.onboarding.diet.DietFragmentDirections
import dev.anmatolay.lirael.util.extension.navigateTo
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

    override fun onResume() {
        super.onResume()

        binding.image.load(R.drawable.cat_onboarding_premium)
        binding.run {
            // TODO persist selection (positive, negative, neutral)
            positiveButton.setOnClickListener {
                navigateTo(PremiumFragmentDirections.actionToMainNavGraph())
            }
        }
    }

    override fun onDestroyView() {
        // TODO: re-enable bottom nav and toolbar actions
        binding.image.dispose()
        super.onDestroyView()
    }
}
