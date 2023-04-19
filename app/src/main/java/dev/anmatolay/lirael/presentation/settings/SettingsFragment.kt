package dev.anmatolay.lirael.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.core.presentation.*
import dev.anmatolay.lirael.presentation.Event
import dev.anmatolay.lirael.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BasePreferenceFragment<SettingsEvent>() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override val viewModel by viewModel<SettingsViewModel>()

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
    }
}
