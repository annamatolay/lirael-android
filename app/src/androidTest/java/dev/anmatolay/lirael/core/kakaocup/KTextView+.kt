package dev.anmatolay.lirael.core.kakaocup

import androidx.annotation.FontRes
import androidx.test.espresso.assertion.ViewAssertions
import dev.anmatolay.lirael.core.kakaocup.matcher.AllCapsMatcher
import dev.anmatolay.lirael.core.kakaocup.matcher.TypefaceMatcher
import io.github.kakaocup.kakao.text.KTextView

fun KTextView.hasFont(@FontRes id: Int) {
    view.check(
        ViewAssertions.matches(
            TypefaceMatcher(id)
        )
    )
}

fun KTextView.isAllCaps() {
    view.check(
        ViewAssertions.matches(
            AllCapsMatcher()
        )
    )
}
