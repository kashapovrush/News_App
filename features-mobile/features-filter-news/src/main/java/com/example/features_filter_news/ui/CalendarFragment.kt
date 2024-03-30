package com.example.features_filter_news.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.features_filter_news.R
import com.example.features_filter_news.databinding.FragmentCalendarBinding
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_CURRENT_DATE
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_CURRENT_DATE_TWO
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_DATE
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.CALENDAR_FRAGMENT_SCREEN
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.EXTRA_DATA
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.STATE_SCREEN
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class CalendarFragment : Fragment() {

    private lateinit var localDate: LocalDate
    private lateinit var binding: FragmentCalendarBinding
    private var currentScreen = EDIT_DATE_SCREEN
    private var daysInMonth = ArrayList<String>()
    private var daysShowText = EMPTY_TEXT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_edit, EditDateFragment.newInstance())
        }

        parentFragment?.setFragmentResult(STATE_SCREEN, bundleOf(EXTRA_DATA to CALENDAR_FRAGMENT_SCREEN))
        localDate = LocalDate.now()

        setMonth()

        binding.btnCancelChoose.setOnClickListener {
            if (currentScreen == EDIT_YEAR_SCREEN) {
                childFragmentManager.commit {
                    replace(R.id.container_for_edit, EditDateFragment.newInstance())
                }

                binding.monthYearTv.text = monthYearFromDate(LocalDate.now())
            } else {
                childFragmentManager.commit {
                    replace(R.id.container_for_edit, EditDateFragment.newInstance())
                }

                binding.monthYearTv.text = monthYearFromDate(LocalDate.now())
                binding.currentDatesTv.text = EMPTY_TEXT

            }
        }

        binding.btnOkChoose.setOnClickListener {
            if (currentScreen == EDIT_YEAR_SCREEN) {
                setFragmentResultListener(EXTRA_YEAR) { _, bundle ->
                    val wrapper =
                        bundle.getParcelable<LocalDateParcelable>(EditDateFragment.EXTRA_DATE)
                    localDate = wrapper?.localDate ?: LocalDate.now()
                    binding.monthYearTv.text = monthYearFromDate(localDate)
                    setMonth()
                }
                val wrapper = LocalDateParcelable(localDate)
                childFragmentManager.setFragmentResult(
                    EXTRA_DATE_TO_CALENDAR,
                    bundleOf(EditDateFragment.EXTRA_DATE to wrapper)
                )
                currentScreen = EDIT_DATE_SCREEN
                childFragmentManager.commit {
                    replace(R.id.container_for_edit, EditDateFragment.newInstance())
                }
            } else {
                (parentFragment as ClickListenerFromCalendar).clickListenerToChoose()
            }
        }

        binding.btnPreviousMonth.setOnClickListener {
            daysInMonth.clear()
            localDate = localDate.minusMonths(1)
            val wrapper = LocalDateParcelable(localDate)
            childFragmentManager.setFragmentResult(
                EditDateFragment.EXTRA_PLUS,
                bundleOf(EditDateFragment.EXTRA_DATE to wrapper)
            )
            setMonth()
        }

        binding.btnNextMonth.setOnClickListener {
            daysInMonth.clear()
            localDate = localDate.plusMonths(1)
            val wrapper = LocalDateParcelable(localDate)
            childFragmentManager.setFragmentResult(
                EditDateFragment.EXTRA_MINUS,
                bundleOf(EditDateFragment.EXTRA_DATE to wrapper)
            )
            setMonth()
        }

        binding.btnChangeYear.setOnClickListener {
            if (currentScreen == EDIT_YEAR_SCREEN) {
                currentScreen = EDIT_DATE_SCREEN
                childFragmentManager.commit {
                    replace(R.id.container_for_edit, EditDateFragment.newInstance())
                }
            } else {
                currentScreen = EDIT_YEAR_SCREEN
                childFragmentManager.clearFragmentResult(EXTRA_YEAR)
                childFragmentManager.commit {
                    replace(R.id.container_for_edit, EditYearFragment.newInstance())
                }
            }
        }



        setFragmentResultListener(EXTRA_CURRENT_DATE) { _, bundle ->
            val wrapperOne = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            val localDateOne = wrapperOne?.localDate ?: LocalDate.now()
            daysShowText = monthWithDayFromDate(localDateOne)

            binding.currentDatesTv.text = daysShowText

        }

        setFragmentResultListener(EXTRA_CURRENT_DATE_TWO) { _, bundle ->

            val wrapperTwo = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            val localDateTwo = wrapperTwo?.localDate ?: LocalDate.now()
            binding.currentDatesTv.text = daysShowText + "-" + monthWithDayFromDate(localDateTwo)

        }

    }

    fun setMonth() {
        binding.monthYearTv.text = monthYearFromDate(localDate)
    }


    fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    fun monthWithDayFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)
        return date.format(formatter)
    }

    interface ClickListenerFromCalendar {

        fun clickListenerToChoose()
    }


    companion object {

        const val EDIT_YEAR_SCREEN = 1
        const val EDIT_DATE_SCREEN = 2

        const val EXTRA_YEAR = "year"
        const val EXTRA_DATE_TO_CALENDAR = "date_to_calendar"

        const val TAG = "MainActivityTest"

        const val EMPTY_TEXT = ""

        fun newInstance() = CalendarFragment()
    }
}