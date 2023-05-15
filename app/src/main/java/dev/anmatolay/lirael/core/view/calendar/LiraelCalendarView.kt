package dev.anmatolay.lirael.core.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import dev.anmatolay.lirael.core.view.calendar.adapter.CalendarAdapter
import dev.anmatolay.lirael.core.view.calendar.adapter.Day
import dev.anmatolay.lirael.core.view.calendar.adapter.Day.Companion.toMarkedDay
import dev.anmatolay.lirael.core.view.calendar.adapter.Day.Companion.toNotMarkedDay
import dev.anmatolay.lirael.core.view.calendar.adapter.Day.Companion.toNotMarkedDays
import dev.anmatolay.lirael.databinding.LayoutCalendarLiraelBinding
import dev.anmatolay.lirael.domain.model.CookingHistory
import dev.anmatolay.lirael.domain.usecase.cooking.GetCookingHistoryUseCase
import dev.anmatolay.lirael.util.LocalDateProvider
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate
import java.time.YearMonth

// TODO: Add swipe support
class LiraelCalendarView(
    context: Context,
    attrs: AttributeSet?,
) : LinearLayout(context, attrs) {

    private val binding: LayoutCalendarLiraelBinding

    private val localDateProvider by inject<LocalDateProvider>(LocalDateProvider::class.java)
    private val getCookingHistoryUseCase by inject<GetCookingHistoryUseCase>(GetCookingHistoryUseCase::class.java)
    private var selectedDate = localDateProvider.now()

    // TODO: make it map (key: formatted date, value list of dayOfMonth) to access marked days O(1) (instead of O(n) with filtering)
    private var markedDates = mutableListOf<Pair<LocalDate, Int>>()

    init {
        binding = LayoutCalendarLiraelBinding.inflate(LayoutInflater.from(context), this, false)

        with(binding) {
            backButton.setOnClickListener {
                selectedDate = selectedDate.minusMonths(1)
                updateMarkedDatesIfAny()
                setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
            }
            forwardButton.setOnClickListener {
                selectedDate = selectedDate.plusMonths(1)
                updateMarkedDatesIfAny()
                setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
            }
        }

        // Make calendar visible in the IDE
        if (isInEditMode)
            setUpCalendar(withMarkedDays = false)
    }

    // first: Epoch date, cooked count
    fun setUpCalendar(markedDays: List<CookingHistory> = emptyList()) {
        if (markedDays.isNotEmpty()) {
            this.markedDates.addAll(
                markedDays.map { LocalDate.ofEpochDay(it.epochDateTime) to it.numberOfCooking }
            )
            this.markedDates = this.markedDates.distinct().toMutableList()
        }
        setUpCalendar(withMarkedDays = markedDates.isNotEmpty())
    }

    private fun updateMarkedDatesIfAny() {
        getCookingHistoryUseCase(localDateProvider.getFormattedMonthAndYear(selectedDate))?.let {
            markedDates.addAll(
                it.map { cookingHistory ->
                    LocalDate.ofEpochDay(cookingHistory.epochDateTime) to cookingHistory.numberOfCooking
                }
            )
        }
    }

    private fun setUpCalendar(withMarkedDays: Boolean) {
        val daysInMonth =
            if (withMarkedDays)
                markedDaysInMonth()
            else
                stringDaysInMonth().toNotMarkedDays()

        with(binding) {
            monthYearText.text = localDateProvider.getFormattedMonthAndYear(selectedDate)
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
                .filter { selectedDate.month.equals(it.first.month) && selectedDate.year == it.first.year }
                .associate { it.first.dayOfMonth.toString() to it.second }

        val markedDaysInMonth =
            stringDaysInMonth()
                .map {
                    if (stringMarkedDay.containsKey(it))
                        it.toMarkedDay(stringMarkedDay[it]!!)
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
