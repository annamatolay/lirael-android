package dev.anmatolay.lirael.presentation.dialog.deletion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.BaseDialogFragment
import dev.anmatolay.lirael.databinding.FragmentDialogConfirmBinding
import dev.anmatolay.lirael.util.extension.mainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeletionConfirmationDialogFragment : BaseDialogFragment<DeletionConfirmationEvent>() {

    private lateinit var binding: FragmentDialogConfirmBinding
    override val viewModel by viewModel<DeletionConfirmationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentDialogConfirmBinding.inflate(inflater, container, false)
            .apply {
                binding = this
                binding.checkbox.isVisible = false
            }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            title.text = getString(R.string.confirm_deletion_dialog_title)
            description.text = getString(R.string.confirm_deletion_dialog_description)
            positiveButton.setOnClickListener {
                triggerEvent(DeletionConfirmationEvent.DeletionConfirmed)
                dismiss()
            }
            negativeButton.setOnClickListener { dismiss() }
        }

        viewModel.uiState.observe { state ->
            if (state.isUserDeletionComplete)
                mainActivity()
                    .makeSnackbar(R.string.user_deletion_completed)
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            // IllegalStateException thrown by DeletionConfirmationDialogFragment because not attached
                            // but requireActivity().finish() have to be called to properly close the app
                            // (not working with activity?.finish())
                            requireParentFragment().requireActivity().finish()
                        }
                    })
                    .show()
            if (state.error != null)
                mainActivity()
                    .makeErrorSnackbar(R.string.user_deletion_error) {
                        triggerEvent(DeletionConfirmationEvent.RetryDeleteUserOnClicked)
                    }
                    .show()
        }
    }
}
