package dev.anmatolay.lirael.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.core.presentation.BaseDialogFragment
import dev.anmatolay.lirael.databinding.FragmentDialogConfirmExitBinding
import dev.anmatolay.lirael.presentation.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExitConfirmationDialogFragment : BaseDialogFragment<ExitConfirmationEvent>() {

    private lateinit var binding: FragmentDialogConfirmExitBinding
    override val viewModel by viewModel<ExitConfirmationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentDialogConfirmExitBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            positiveButton.setOnClickListener { activity?.finish() }
            negativeButton.setOnClickListener { dismiss() }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                triggerEvent(ExitConfirmationEvent.NotShowDialogCheckboxChanged(isChecked))
            }
        }

        viewModel.uiState.observe { state ->
            (activity as? MainActivity)?.shouldShowExitConfirmationDialog = !state.isGone
        }
    }
}
