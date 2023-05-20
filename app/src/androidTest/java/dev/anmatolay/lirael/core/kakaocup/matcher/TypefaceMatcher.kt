package dev.anmatolay.lirael.core.kakaocup.matcher

import android.graphics.Typeface
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

// Modified copy of kakao.common.matchers.TypefaceMatcher
class TypefaceMatcher(@FontRes val id: Int) : BoundedMatcher<View, TextView>(TextView::class.java) {
    private var viewTypeface: Typeface? = null
    private var typeface: Typeface? = null

    override fun describeTo(description: Description) {
        description.appendText("Typeface doesn't match")
        description.appendText("Expected: ${typeface?.toOutput()}")
        description.appendText("Actual: ${viewTypeface?.toOutput()}")
    }

    override fun matchesSafely(textView: TextView): Boolean {
        typeface = ResourcesCompat.getFont(textView.context, id)!!
        viewTypeface = textView.typeface
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            textView.typeface.equalsPost28(typeface!!)
        } else {
            textView.typeface.equalsPre28(typeface!!)
        }
    }

    private fun Typeface.toOutput(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine("Style: ${this.style}")
        stringBuilder.appendLine("Bold: ${this.isBold}")
        stringBuilder.appendLine("Italic: ${this.isItalic}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            stringBuilder.appendLine("Weight: ${this.weight}")
        }
        return stringBuilder.toString()
    }

    private fun Typeface.equalsPost28(typeface: Typeface): Boolean =
        typeface.style == style && typeface.weight == weight && typeface.isBold == isBold && typeface.isItalic == isItalic

    private fun Typeface.equalsPre28(typeface: Typeface): Boolean =
        typeface.style == style && typeface.isBold == isBold && typeface.isItalic == isItalic
}
