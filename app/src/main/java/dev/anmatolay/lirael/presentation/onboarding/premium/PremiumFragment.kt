package dev.anmatolay.lirael.presentation.onboarding.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.dispose
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.SharedPrefHandler
import dev.anmatolay.lirael.core.analytics.AnalyticsWrapper
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingPremiumBinding
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class PremiumFragment : BaseFragment<PremiumEvent>() {

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
            positiveButton.setOnClickListener {
                triggerEvent(PremiumEvent.OnPositiveButtonClicked)
            }
            negativeButton.setOnClickListener {
                triggerEvent(PremiumEvent.OnNegativeButtonClicked)
            }
            neutralButton.setOnClickListener {
                triggerEvent(PremiumEvent.OnNeutralButtonClicked)
            }
        }

        viewModel.uiState.observe { state ->
            if (state.isDataPersisted) navigateTo(PremiumFragmentDirections.actionToMainNavGraph())
        }
    }

    override fun onDestroyView() {
        binding.image.dispose()
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()


    }
}
