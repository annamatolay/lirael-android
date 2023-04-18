package dev.anmatolay.lirael.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.FragmentContainerSettingsBinding

class SettingsContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentContainerSettingsBinding.inflate(inflater, container, false)
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.settings_layout, SettingsFragment())
                ?.commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Messing with navigation to let user open settings again and again
        activity?.findViewById<View>(R.id.settings_item)?.isVisible = false
        // recreate app bar icons enabling settings too
        activity?.findViewById<View>(R.id.ui_mode_item)?.isVisible = false
    }

    override fun onDetach() {
        super.onDetach()

        activity?.findViewById<View>(R.id.settings_item)?.isVisible = true
        activity?.findViewById<View>(R.id.ui_mode_item)?.isVisible = true
    }
}
