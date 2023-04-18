package dev.anmatolay.lirael.presentation.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import dev.anmatolay.lirael.R

class SettingsFragment  : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefKey = resources.getString(R.string.key_pref_firebase_all)
        findPreference<SwitchPreferenceCompat>(prefKey)?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) {
                AlertDialog.Builder(context)
                    .setIcon(R.drawable.ic_dialog_sad)
                    .setTitle("Disable data sending")
                    .setMessage("Privacy is really important for me too. I get it. Butt this would help a lot " +
                            "if you would let it. Everything send really anonymously. I promise." +
                            "\n\nAre you sure you want to do that?\nPlease consider to enable this.")
                    .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
            true
        }
    }
}
