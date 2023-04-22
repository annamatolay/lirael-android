package dev.anmatolay.lirael.presentation.onboarding.name

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseFragment
import dev.anmatolay.lirael.databinding.FragmentOnboardingNameBinding
import dev.anmatolay.lirael.util.extension.isLetters
import dev.anmatolay.lirael.util.extension.navigateTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class NameFragment : BaseFragment<NameEvent>() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.image.load(R.drawable.cat_onboarding_name)

        binding.nextButton.setOnClickListener {
            binding.textInputLayout.error = null
            if (binding.textInputLayout.editText?.text?.isValid() == false)
                binding.textInputLayout.error = getString(R.string.input_text_error)
            else
                triggerEvent(NameEvent.UpdateUserName(binding.textInputLayout.editText!!.text.toString()))
        }

        binding.skipButton.setOnClickListener {
            binding.textInputLayout.error = null
            triggerEvent(NameEvent.UpdateUserName(getString(R.string.user_default_name)))
        }

        viewModel.uiState.observe { state ->
            binding.apply {
                nextButton.isEnabled = !state.isLoading
                skipButton.isEnabled = !state.isLoading
                textInputLayout.isInvisible = state.isLoading
                loading.isVisible = state.isLoading
            }

            if (state.isUpdateCompleted) navigateTo(NameFragmentDirections.actionToDietFragment())
        }

    }

    private fun Editable.isValid(): Boolean =
        this.toString().run {
            isLetters() && !isNullOrBlank()
        }
}
