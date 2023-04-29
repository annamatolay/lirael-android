package dev.anmatolay.lirael.presentation.onboarding.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.dispose
import coil.load
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingWelcomeBinding
import dev.anmatolay.lirael.util.extension.mainActivity
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : BaseFragment<WelcomeEvent>() {

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

    override fun onResume() {
        super.onResume()

        binding.termSwitch.setOnCheckedChangeListener { _, isAccapted ->
            if (isAccapted) {
                triggerEvent(WelcomeEvent.TermAndConditionAccepted)
            }
        }

        binding.termLink.setOnClickListener {
            navigateTo(WelcomeFragmentDirections.actionToTermAndConditionsFragment())
        }

        viewModel.uiState.observe { state ->
            if (state.isLoading) {
                binding.run {
                    image.apply {
                        isVisible = false
                        dispose()
                    }
                    loading.isVisible = true
                    termSwitch.isEnabled = false
                    termLink.run {
                        isEnabled = false
                        alpha = 0.3f
                    }
                }
            } else {
                binding.run {
                    image.apply {
                        isVisible = true
                        load(R.drawable.cat_onboarding_welcome)
                    }
                    loading.isVisible = false
                    termSwitch.isEnabled = true
                    termLink.run {
                        isEnabled = true
                        alpha = 1.0f
                    }
                }
            }
            if (state.isUserCreationCompleted) navigateTo(WelcomeFragmentDirections.actionToNameFragment())
            if (state.error != null) {
                mainActivity().makeSnackbar(R.string.onboarding_welcome_error_user_creation, Snackbar.LENGTH_LONG)
            }
        }
    }
}
