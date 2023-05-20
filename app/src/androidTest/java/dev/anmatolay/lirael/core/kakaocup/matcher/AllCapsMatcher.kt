package dev.anmatolay.lirael.core.kakaocup.matcher

import android.view.View
import android.widget.TextView
import androidx.appcompat.text.AllCapsTransformationMethod
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description


class AllCapsMatcher : BoundedMatcher<View, TextView>(TextView::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Text is not all caps.")
    }

    override fun matchesSafely(textView: TextView): Boolean {
        var isAllCaps = textView.isAllCaps
        if (!isAllCaps) {
            isAllCaps =
                AllCapsTransformationMethod::class.java.javaClass.isInstance(textView.transformationMethod.javaClass)
        }
        return isAllCaps
    }
}
