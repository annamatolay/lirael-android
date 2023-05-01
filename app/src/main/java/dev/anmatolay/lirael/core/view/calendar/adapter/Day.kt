package dev.anmatolay.lirael.core.view.calendar.adapter

data class Day(val value: String, val cooked: Int = 0) {
    companion object {
        fun String.toMarkedDay(cooked: Int) = Day(this, cooked)
        fun String.toNotMarkedDay() = Day(this)
        fun List<String>.toNotMarkedDays() = this.map { it.toNotMarkedDay() }
    }
}
