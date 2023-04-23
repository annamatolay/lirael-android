package dev.anmatolay.lirael.presentation.onboarding.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.anmatolay.lirael.BuildConfig
import dev.anmatolay.lirael.databinding.FragmentWebviewBinding
import dev.anmatolay.lirael.util.extension.mainActivity

class TermAndConditionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentWebviewBinding.inflate(inflater, container, false)
            .apply {
                this.webview.apply {
                    loadUrl(BuildConfig.TC_URL)
                    webViewClient = WebViewClient()
                    mainActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            if (canGoBack()) {
                                goBack()
                            } else {
                                findNavController().popBackStack()
                            }
                        }
                    })
                }
            }
            .root
}
