package dev.anmatolay.lirael.core.view.calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.anmatolay.lirael.R
import dev.anmatolay.lirael.databinding.LayoutCalendarLiraelItemBinding
import dev.anmatolay.lirael.util.extension.isDarkModeEnable
import java.time.LocalDate

class CalendarAdapter(private val daysInMonth: List<Day>, private val isCurrentYearAndMonth: Boolean) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(val binding: LayoutCalendarLiraelItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = LayoutCalendarLiraelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = daysInMonth.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val day = daysInMonth[position]
        if (day.value.isEmpty()) {
            holder.binding.dayLayout.background = null
        } else {
            holder.binding.dayTextView.text = daysInMonth[position].value
            if (day.isEqualWithToday() && isCurrentYearAndMonth)
                holder.binding.dayLayout.setBackgroundResource(R.drawable.bg_calendar_current)
            else if (day.isMarked) {
                holder.binding.dayLayout.setBackgroundResource(R.drawable.bg_calendar_marked)

                val resources = holder.itemView.context.resources
                val isDarkModeEnable = resources.isDarkModeEnable()
                holder.binding.dayTextView.setTextColor(
                    resources.getColor(
                        if (isDarkModeEnable) R.color.black else R.color.white,
                        null
                    )
                )
            }
        }
    }

    private fun Day.isEqualWithToday() =
        this.value == LocalDate.now().dayOfMonth.toString()
}
