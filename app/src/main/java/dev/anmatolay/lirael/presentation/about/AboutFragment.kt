package dev.anmatolay.lirael.presentation.about

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentAboutBinding.inflate(inflater, container, false)
            .apply {
                val text = getString(R.string.about_app_version, BuildConfig.VERSION_NAME)
                appVersion.text = Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
                appVersionContainer.setOnClickListener { copyAppVersion() }
                storePageContainer.setOnClickListener { openPlayStorePage() }
            }
            .root

    override fun onResume() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.GONE
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.VISIBLE
    }

    private fun FragmentAboutBinding.copyAppVersion() {
        val clipboardManager = getSystemService(this.root.context, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("app version", BuildConfig.VERSION_NAME)
        clipboardManager?.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    private fun openPlayStorePage() {
        val packageName = BuildConfig.APPLICATION_ID
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}
