package dev.anmatolay.lirael.core.view.calendar.adapter

data class Day(val value: String, val isMarked: Boolean = false) {
    companion object {
        fun String.toMarkedDay() = Day(this, true)
        fun String.toNotMarkedDay() = Day(this, false)
        fun List<String>.toNotMarkedDays() = this.map { it.toNotMarkedDay() }
    }
}
