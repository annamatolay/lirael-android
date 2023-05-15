package dev.anmatolay.lirael.presentation.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentAboutBinding.inflate(inflater, container, false)
            .root

    override fun onResume() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.GONE
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.VISIBLE
    }
}
