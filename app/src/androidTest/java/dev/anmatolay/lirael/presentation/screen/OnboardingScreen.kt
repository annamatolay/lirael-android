package dev.anmatolay.lirael.presentation.screen

import androidx.test.espresso.DataInteraction
import dev.anmatolay.lirael.R
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.list.KAdapterItem
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.switch.KSwitch
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class OnboardingScreen : Screen<OnboardingScreen>() {
    val title = KTextView {withId(R.id.title) }
    val subtitle = KTextView { withId(R.id.subtitle) }
    val description = KTextView { withId(R.id.description) }
    val image = KImageView { withId(R.id.image) }
    val loading = KProgressBar { withId(R.id.loading) }
    // NameScreen & DietScreen
    val nextButton = KButton { withId(R.id.next_button) }

    // WelcomeScreen
    val termsSwitch = KSwitch { withId(R.id.term_switch) }
    val termsLink = KTextView { withId(R.id.term_link) }

    // NameScreen
    val textInputLayout = KTextInputLayout { withId(R.id.text_input_layout) }
    val skipButton = KButton { withId(R.id.skip_button) }

    // DietScreen
    val spinner = KSpinner(builder = { withId(R.id.next_button) }, itemTypeBuilder = { itemType(OnboardingScreen::Item) })

    // PremiumScreen
    val positiveButton = KButton { withId(R.id.positive_button) }
    val neutralButton = KButton { withId(R.id.neutral_button) }
    val negativeButton = KButton { withId(R.id.negative_button) }

    class Item(i: DataInteraction) : KAdapterItem<String>(i) {
        val item = KTextView(i) { withId(R.id.title) }
    }
}
