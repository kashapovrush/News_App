package com.example.features_filter_news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.features_filter_news.adapter.date_adapter.CalendarAdapter
import com.example.features_filter_news.databinding.FragmentEditDateBinding
import com.example.features_filter_news.model.Day
import com.example.features_filter_news.ui.CalendarFragment.Companion.TAG
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class EditDateFragment : Fragment(), CalendarAdapter.OnItemClickListener {

    private lateinit var binding: FragmentEditDateBinding
    private lateinit var localDate: LocalDate
    private lateinit var calendarAdapter: CalendarAdapter
    private var fixDaysList = ArrayList<Day>()
    private var enabledDaysList = ArrayList<Day>()
    private var currentDaysList = ArrayList<Day>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDateBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localDate = LocalDate.now()

        fixDaysList = daysInMonthList(localDate)

        setMonth(fixDaysList)

        setFragmentResultListener(CalendarFragment.EXTRA_DATE_TO_CALENDAR) { _, bundle ->
            val wrapper = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            localDate = wrapper?.localDate ?: LocalDate.now()
            fixDaysList = daysInMonthList(localDate)
            setMonth(fixDaysList)
        }

        setFragmentResultListener(EXTRA_MINUS) { _, bundle ->
            val wrapper = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            localDate = wrapper?.localDate ?: LocalDate.now()
            fixDaysList = daysInMonthList(localDate)
            setMonth(fixDaysList)
        }

        setFragmentResultListener(EXTRA_PLUS) { _, bundle ->
            val wrapper = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            localDate = wrapper?.localDate ?: LocalDate.now()
            fixDaysList = daysInMonthList(localDate)
            setMonth(fixDaysList)
        }
    }


    fun setMonth(list: ArrayList<Day>) {
        calendarAdapter =
            CalendarAdapter(list, requireContext(), this)
        with(binding.daysRecyclerView) {
            setLayoutManager(GridLayoutManager(requireContext(), 7))
            adapter = calendarAdapter
        }

        calendarAdapter.onClickListener = {
            if (it.value != "") {
                val currentDay = localDate.withDayOfMonth(it.value.toInt())
                parentFragment?.setFragmentResult(
                    EXTRA_CURRENT_DATE,
                    bundleOf(EXTRA_DATE to monthWithDayFromDate(currentDay))
                )
            }
        }
    }


    private fun daysInMonthList(date: LocalDate?): ArrayList<Day> {
        val daysInMonthArray = ArrayList<Day>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = localDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.getDayOfWeek().value


        for (i in 1..42) {
            enabledDaysList.clear()
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                if (dayOfWeek != 7) {
                    daysInMonthArray.add(Day(""))
                }
            } else {
                if (i <= daysInMonth + dayOfWeek) {
                    if (LocalDate.now() == localDate.withDayOfMonth(i - dayOfWeek)) {
                        daysInMonthArray.add(
                            Day(
                                (i - dayOfWeek).toString(),
                                isCurrentDay = Day.TODAY
                            )
                        )
                    } else {
                        daysInMonthArray.add(Day((i - dayOfWeek).toString()))
                    }
                }
            }
        }
        return daysInMonthArray
    }


    fun monthWithDayFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)
        return date.format(formatter)
    }

    companion object {

        const val EXTRA_MINUS = "minus"
        const val EXTRA_PLUS = "plus"
        const val EXTRA_DATE = "date"
        const val EXTRA_CURRENT_DATE = "current_day"
        const val EXTRA_CURRENT_DATE_TWO = "current_day_two"

        fun newInstance() = EditDateFragment()
    }

    override fun selectedItem(day: Day) {
        currentDaysList.clear()
        val dayEnabled = Day(day.value, isEnabled = Day.ENABLED)
        if (enabledDaysList.size == 1) {
            if (dayEnabled.value.toInt() < enabledDaysList[0].value.toInt()) {
                val lastDay = enabledDaysList[0]
                enabledDaysList.clear()
                enabledDaysList.add(dayEnabled)
                enabledDaysList.add(lastDay)
            } else {
                enabledDaysList.add(dayEnabled)
            }
        } else if (enabledDaysList.size == 2) {
            enabledDaysList.clear()
            enabledDaysList.add(dayEnabled)
        } else {
            enabledDaysList.add(dayEnabled)
        }

        if (enabledDaysList.size == 1) {
            val localDateOne = localDate.withDayOfMonth(enabledDaysList[0].value.toInt())
            val wrapperOne = LocalDateParcelable(localDateOne)
            parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE, bundleOf(EXTRA_DATE to wrapperOne))
            parentFragment?.parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE, bundleOf(EXTRA_DATE to wrapperOne))
        }

        if (enabledDaysList.size == 2) {
            val localDateOne = localDate.withDayOfMonth(enabledDaysList[0].value.toInt())
            val wrapperOne = LocalDateParcelable(localDateOne)
            parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE, bundleOf(EXTRA_DATE to wrapperOne))
            parentFragment?.parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE, bundleOf(EXTRA_DATE to wrapperOne))

            val localDateTwo = localDate.withDayOfMonth(enabledDaysList[1].value.toInt())
            val wrapperTwo = LocalDateParcelable(localDateTwo)
            parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE_TWO, bundleOf(EXTRA_DATE to wrapperTwo))
            parentFragment?.parentFragment?.setFragmentResult(EXTRA_CURRENT_DATE_TWO, bundleOf(EXTRA_DATE to wrapperTwo))
        }


        fixDaysList.forEach { day ->
            if (enabledDaysList.size == 1) {
                if (day.value == dayEnabled.value) {
                    currentDaysList.add(enabledDaysList[0])
                } else {
                    currentDaysList.add(day)
                }
            } else if (enabledDaysList.size == 2) {
                if (day.value == enabledDaysList[0].value) {
                    currentDaysList.add(enabledDaysList[0])
                } else if (day.value == enabledDaysList[1].value) {
                    currentDaysList.add(enabledDaysList[1])
                } else if (day.value != "") {
                    if (day.value.toInt() > enabledDaysList[0].value.toInt()
                        && day.value.toInt() < enabledDaysList[1].value.toInt()
                    ) {
                        currentDaysList.add(Day(day.value, isBetween = Day.IS_BETWEEN))
                    } else {
                        currentDaysList.add(day)
                    }
                } else {
                    currentDaysList.add(day)
                }
            } else if (enabledDaysList.size == 3) {
                enabledDaysList.clear()
                currentDaysList.add(day)
            } else if (enabledDaysList.size == 0) {
                currentDaysList.add(day)
            }
        }
        setMonth(currentDaysList)
    }
}