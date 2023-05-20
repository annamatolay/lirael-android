package dev.anmatolay.lirael.core.kakaocup

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.test.espresso.assertion.ViewAssertions
import dev.anmatolay.lirael.core.kakaocup.matcher.DrawableMatcher
import io.github.kakaocup.kakao.common.views.KView

// FIXME
@Suppress("unused")
fun KView.hasDrawable(@DrawableRes resId: Int, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) {
    view.check(ViewAssertions.matches(DrawableMatcher(resId = resId, toBitmap = toBitmap)))
}
