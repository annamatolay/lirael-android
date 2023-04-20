package dev.anmatolay.lirael.presentation.onboarding.diet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.dispose
import coil.load
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingDietBinding
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.custom.CustomViewModel
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DietFragment : BaseFragment<DietEvent>() {

    override val viewModel by viewModel<DietViewModel>()
    private lateinit var binding: FragmentOnboardingDietBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardingDietBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onResume() {
        super.onResume()

        binding.image.load(R.drawable.cat_onboarding_diet)
        binding.run {
            nextButton.setOnClickListener {
                triggerEvent(DietEvent.DietSelected(spinner.selectedItemPosition))
                Timber.d("spinner.selectedItemPosition: ${spinner.selectedItemPosition}")
                navigateTo(DietFragmentDirections.actionToPremiumFragment())
            }
        }
    }

    override fun onDestroyView() {
        binding.image.dispose()
        super.onDestroyView()
    }
}
