package dev.anmatolay.lirael.core.kakaocup.matcher

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.BoundedDiagnosingMatcher
import org.hamcrest.Description
import java.util.*

class ToolbarTitleColorMatcher(@StringRes val colorResId: Int) :
    BoundedDiagnosingMatcher<View, Toolbar>(Toolbar::class.java) {

    override fun describeMoreTo(description: Description) {
        description.appendText("Toolbar title color is not equals to ${getColorHex(colorResId)}")
    }

    override fun matchesSafely(view: Toolbar, mismatchDescription: Description): Boolean {
        val textViewColor = view.getTitleTextView()?.currentTextColor

        val expectedColor: Int = if (Build.VERSION.SDK_INT <= 22) {
            @Suppress("DEPRECATION")
            view.context.resources.getColor(colorResId)
        } else {
            view.context.getColor(colorResId)
        }

        mismatchDescription
            .appendText("textView.currentTextColor was ")
            .appendText(textViewColor?.let { getColorHex(it) })
        return textViewColor == expectedColor
    }

    // Toolbar getTitleTextView is restricted
    private fun Toolbar.getTitleTextView(): TextView? {
        var textView: TextView? = null
        for (i in 0 until childCount) {
            if (TextView::class.java.javaClass.isInstance(getChildAt(i).javaClass)) {
                textView = getChildAt(i) as TextView
                break
            }
        }
        return textView
    }

    private fun getColorHex(colorResId: Int): String {
        return String.format(
            Locale.ROOT, "#%02X%06X", 0xFF and Color.alpha(colorResId), 0xFFFFFF and colorResId
        )
    }
}

