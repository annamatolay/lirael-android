package dev.anmatolay.lirael.core.kakaocup.matcher

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import dev.anmatolay.lirael.core.kakaocup.toBitmap
import io.github.kakaocup.kakao.common.utilities.getResourceColor
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

// Modified copy of Takao.common.matchers.DrawableMatcher
// This works with any view that has background not only with ImageView
class DrawableMatcher(
    @DrawableRes private val resId: Int = -1,
    private val drawable: Drawable? = null,
    @ColorRes private val tintColorId: Int? = null,
    private val toBitmap: ((drawable: Drawable) -> Bitmap)? = null
) : TypeSafeMatcher<View>(View::class.java) {

    override fun describeTo(desc: Description) {
        desc.appendText("with drawable id $resId or provided instance")
    }

    override fun matchesSafely(view: View?): Boolean {
        if (drawable == null) {
            return false
        }

        if (resId < 0) {
            return view?.background == null
        }

        return view?.let {
            var expectedDrawable = drawable

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                expectedDrawable = DrawableCompat.wrap(expectedDrawable).mutate()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tintColorId?.let { tintColorId ->
                    val tintColor = getResourceColor(tintColorId)
                    expectedDrawable.apply {
                        setTintList(ColorStateList.valueOf(tintColor))
                        setTintMode(PorterDuff.Mode.SRC_IN)
                    }
                }
            }

            val convertDrawable = view.background.mutate()
            val bitmap = toBitmap?.invoke(convertDrawable) ?: convertDrawable.toBitmap()

            val otherBitmap = toBitmap?.invoke(expectedDrawable) ?: expectedDrawable.toBitmap()

            return bitmap.sameAs(otherBitmap)
        } ?: false
    }
}

