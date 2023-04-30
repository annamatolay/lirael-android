package dev.anmatolay.lirael.util.extension

import android.content.res.Configuration
import android.content.res.Resources

fun Resources.isDarkModeEnable() =
    when (this.configuration.uiMode) {
        // When you change UI mode in setting and warm start app
        // then config values will be exactly one more than it should be
        // https://issuetracker.google.com/issues/134379747
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_YES + 1 -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_NO + 1 -> false
        else -> false
    }
