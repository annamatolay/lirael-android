package dev.anmatolay.lirael.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

interface LocalDateProvider {

    fun now(): LocalDate

    // LLLL is equivalent of MMMM without localization issue  with Java 8 (in some cases, fixed in Java 9)
    fun getDefaultDateTimeFormatter(pattern: String = "LLLL yyyy"): DateTimeFormatter =
        DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
}

class LocalDateProviderImpl : LocalDateProvider {
    override fun now(): LocalDate = LocalDate.now()

}

class FakeLocalDateProvider : LocalDateProvider {
    lateinit var pattern: String

    // YYYY-MM-dd
    override fun now(): LocalDate = LocalDate.parse(pattern)
}

