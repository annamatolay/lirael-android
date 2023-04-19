package dev.anmatolay.lirael.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.snackbar.Snackbar
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.*
import dev.anmatolay.lirael.presentation.dialog.deletion.DeletionConfirmationDialogFragment
import dev.anmatolay.lirael.util.extension.mainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BasePreferenceFragment<SettingsEvent>() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override val viewModel by viewModel<SettingsViewModel>()

    override fun onResume() {
        super.onResume()

        viewModel.uiState.observe { state ->
            if (state.isUserDeletionComplete)
                mainActivity()
                    .makeSnackbar(R.string.user_deletion_completed)
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            mainActivity().finish()
                        }
                    })
                    .show()
            if (state.error != null)
                mainActivity()
                    .makeErrorSnackbar(R.string.user_deletion_error) {
                        triggerEvent(SettingsEvent.RetryDeleteUserOnClicked)
                    }
                    .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<SwitchPreferenceCompat>(resources.getString(R.string.key_pref_firebase_all))
            ?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_dialog_sad)
                        .setTitle("Disable data sending")
                        .setMessage(
                            "Privacy is really important for me too. I get it. Butt this would help a lot " +
                                    "if you would let it. Everything send really anonymously. I promise." +
                                    "\n\nAre you sure you want to do that?\nPlease consider to enable this."
                        )
                        .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                true
            }

        findPreference<EditTextPreference>(resources.getString(R.string.key_pref_user_name))
            ?.setOnPreferenceChangeListener { _, newValue ->
                triggerEvent(SettingsEvent.UsernameChanged(newValue as String))
                true
            }

        findPreference<Preference>(resources.getString(R.string.key_pref_user_delete))
            ?.setOnPreferenceClickListener {
                DeletionConfirmationDialogFragment()
                    .show(mainActivity().supportFragmentManager, null)
                true
            }
    }
}
