package dev.anmatolay.lirael.presentation.dialog.exit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseDialogFragment
import dev.anmatolay.lirael.databinding.FragmentDialogConfirmBinding
import dev.anmatolay.lirael.util.extension.mainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExitConfirmationDialogFragment : BaseDialogFragment<ExitConfirmationEvent>() {

    private lateinit var binding: FragmentDialogConfirmBinding
    override val viewModel by viewModel<ExitConfirmationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentDialogConfirmBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            title.text = getString(R.string.confirm_exit_dialog_title)
            description.text = getString(R.string.confirm_exit_dialog_description)
            positiveButton.setOnClickListener { activity?.finish() }
            negativeButton.setOnClickListener { dismiss() }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                triggerEvent(ExitConfirmationEvent.NotShowDialogCheckboxChanged(isChecked))
            }
        }

        viewModel.uiState.observe { state ->
            mainActivity().shouldShowExitConfirmationDialog = !state.isGone
        }
    }
}
