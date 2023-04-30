package dev.anmatolay.lirael.core.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import dev.anmatolay.lirael.core.view.calendar.Day.Companion.toMarkedDay
import dev.anmatolay.lirael.core.view.calendar.Day.Companion.toNotMarkedDay
import dev.anmatolay.lirael.core.view.calendar.Day.Companion.toNotMarkedDays
import dev.anmatolay.lirael.databinding.LayoutCalendarLiraelBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*


// TODO: Add swipe support
class LiraelCalendarView(
    context: Context,
    attrs: AttributeSet?,
) : LinearLayout(context, attrs) {

    private val binding: LayoutCalendarLiraelBinding
    private var selectedDate = LocalDate.now()
    // TODO: make it map (key: formatted date, value list of dayOfMonth) to access marked days O(1) (instead of O(n) with filtering)
    private var markedDates = mutableListOf<LocalDate>()

    // LLLL is equivalent of MMMM without localization issue  with Java 8 (in some cases, fixed in Java 9)
    private val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale.getDefault())

    init {
        binding = LayoutCalendarLiraelBinding.inflate(LayoutInflater.from(context), this, false)

        with(binding) {
            backButton.setOnClickListener {
                selectedDate = selectedDate.minusMonths(1)
                setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
            }
            forwardButton.setOnClickListener {
                selectedDate = selectedDate.plusMonths(1)
                setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
            }
        }

        // Make calendar visible in the IDE
        if (isInEditMode)
            setUpCalendar(withMarkedDays = false)
    }

    fun setUpCalendar(vararg markedDaysInEpoch: Long) {
        if (markedDates.isNotEmpty()) {
            this.markedDates.addAll(
                markedDaysInEpoch.map { LocalDate.ofEpochDay(it) }
            )
            this.markedDates = this.markedDates.distinct().toMutableList()
        }
        setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
    }

    private fun setUpCalendar(withMarkedDays: Boolean) {
        val daysInMonth =
            if (withMarkedDays)
                markedDaysInMonth()
            else
                stringDaysInMonth().toNotMarkedDays()

        with(binding) {
            monthText.text = selectedDate.format(formatter)
            val layoutManager = GridLayoutManager(context, DAYS_OF_WEEK)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = CalendarAdapter(daysInMonth, selectedDate)
        }

        removeView(binding.root)
        addView(binding.root)
    }

    private fun markedDaysInMonth(): List<Day> {
        val stringMarkedDay =
            // Todo: optimise with map
            this.markedDates
                .filter { selectedDate.month.equals(it.month) }
                .map { it.dayOfMonth.toString() }

        val markedDaysInMonth =
            stringDaysInMonth()
                .map {
                    if (stringMarkedDay.contains(it))
                        it.toMarkedDay()
                    else
                        it.toNotMarkedDay()
                }

        return markedDaysInMonth
    }

    private fun stringDaysInMonth(): List<String> {
        val daysInMonthArray = mutableListOf<String>()
        val daysInMonth = YearMonth.from(selectedDate).lengthOfMonth()
        // Value between 1 and 7 based on the first day of the selected month
        // (e.g.: month starts with Mon., then dayOfWeek value is 1)
        val dayOfWeek = selectedDate.withDayOfMonth(1).dayOfWeek.value

        // 42 = 4 weeks (28) + 2 weeks (14) that partially "owned" by the selected month (first and last week)
        for (index in 1..42) {
            if (index <= dayOfWeek || index > daysInMonth + dayOfWeek) {
                // Adding empty String as blank adapter item if index outside of days of selected month
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((index - dayOfWeek).toString())
            }
        }

        return daysInMonthArray.toList()
    }

    companion object {
        private const val DAYS_OF_WEEK = 7
    }
}
