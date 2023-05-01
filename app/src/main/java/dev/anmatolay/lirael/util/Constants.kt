package dev.anmatolay.lirael.util

import java.time.format.DateTimeFormatter
import java.util.*

object Constants {
    const val KEY_USER_ID = "key_user_id"
    const val USER_DEFAULT_ID = "null"
    const val KEY_HIDE_EXIT_DIALOG = "key_show_exit_dialog"
    const val KEY_OPENED_RECIPE = "key_opened_recipe"
    const val KEY_RECIPE_ADAPTER_ITEM = "key_recipe_adapter_item"
    const val STRING_SEPARATOR = "<>"

    // LLLL is equivalent of MMMM without localization issue  with Java 8 (in some cases, fixed in Java 9)
    fun getDefaultDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale.getDefault())
}
