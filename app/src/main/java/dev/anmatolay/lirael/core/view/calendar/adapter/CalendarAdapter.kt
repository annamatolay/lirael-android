package dev.anmatolay.lirael.core.view.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutCalendarLiraelItemBinding
import dev.anmatolay.lirael.util.Constants
import dev.anmatolay.lirael.util.LocalDateProvider
import dev.anmatolay.lirael.util.extension.isDarkModeEnable
import org.koin.java.KoinJavaComponent
import java.time.LocalDate

class CalendarAdapter(
    private val daysInMonth: List<Day>,
    private val selectedDate: LocalDate,
) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private val localDateProvider by KoinJavaComponent.inject<LocalDateProvider>(LocalDateProvider::class.java)
    private val today = localDateProvider.now()

    class CalendarViewHolder(val binding: LayoutCalendarLiraelItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = LayoutCalendarLiraelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = daysInMonth.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val context = holder.itemView.context
        val resources = context.resources

        val day = daysInMonth[position]
        if (day.value.isEmpty()) {
            holder.binding.dayLayout.background = null
        } else {
            holder.binding.dayTextView.text = daysInMonth[position].value
            if (day.isEqualWithToday())
                holder.binding.dayLayout.setBackgroundResource(R.drawable.bg_calendar_current)
            else if (day.cooked > 0) {
                holder.binding.dayLayout.setBackgroundResource(R.drawable.bg_calendar_marked)

                val isDarkModeEnable = resources.isDarkModeEnable()
                holder.binding.dayTextView.setTextColor(
                    resources.getColor(
                        if (isDarkModeEnable) R.color.black else R.color.white,
                        null
                    )
                )
            }
            holder.itemView.setOnClickListener {
                val text =
                    resources.getQuantityString(
                        R.plurals.calendar_day_cooked_info,
                        day.cooked,
                        day.cooked,
                        day.value,
                        localDateProvider.getFormattedMonthAndYear(selectedDate),
                    )

                Toast.makeText(context, text, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun Day.isEqualWithToday() =
        this.value == today.dayOfMonth.toString() && isCurrentYearAndMonth()

    private fun isCurrentYearAndMonth() =
        selectedDate.month.equals(today.month) && selectedDate.year == today.year
}
