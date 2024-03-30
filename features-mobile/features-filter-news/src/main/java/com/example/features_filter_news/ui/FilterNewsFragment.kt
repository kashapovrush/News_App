package com.example.features_filter_news.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import com.example.features_filter_news.R
import com.example.features_filter_news.databinding.FragmentFilterNewsBinding
import com.example.features_filter_news.di.FilterNewsComponentProvider
import com.example.features_filter_news.ui.CalendarFragment.Companion.EMPTY_TEXT
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.STATE_LANGUAGE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.EXTRA_FROM_DATE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.EXTRA_TO_DATE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.STATE_SEGMENTS
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_CURRENT_DATE
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_CURRENT_DATE_TWO
import com.example.features_filter_news.ui.EditDateFragment.Companion.EXTRA_DATE
import com.example.prefrences.PreferencesManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class FilterNewsFragment : Fragment(),
    ChooseFilterFragment.OnClickListenerFromChooseFilterFragment,
    CalendarFragment.ClickListenerFromCalendar {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private lateinit var binding: FragmentFilterNewsBinding
    private var currentScreen = ""
    val calendarFragment = CalendarFragment.newInstance()
    val chooseFilterFragment = ChooseFilterFragment.newInstance()
    var fromDate = EMPTY_TEXT
    var toDate = EMPTY_TEXT

    override fun onAttach(context: Context) {
        (requireActivity().application as FilterNewsComponentProvider).getFilterNewsComponentProvider()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(STATE_SCREEN) { _, bundle ->
            currentScreen = bundle.getString(EXTRA_DATA) ?: EMPTY_TEXT
        }


        setFragmentResultListener(EXTRA_CURRENT_DATE) { _, bundle ->
            fromDate = EMPTY_TEXT
            toDate = EMPTY_TEXT
            preferencesManager.putString(EXTRA_FROM_DATE, EMPTY_TEXT)
            preferencesManager.putString(EXTRA_TO_DATE, EMPTY_TEXT)
            val wrapper = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            val localDate = wrapper?.localDate ?: LocalDate.now()
            fromDate = monthWithDayWithYearFromDate(localDate)
            preferencesManager.putString(EXTRA_FROM_DATE, fromDate)
        }

        setFragmentResultListener(EXTRA_CURRENT_DATE_TWO) { _, bundle ->
            val wrapper = bundle.getParcelable<LocalDateParcelable>(EXTRA_DATE)
            val localDate = wrapper?.localDate ?: LocalDate.now()
            toDate = monthWithDayWithYearFromDate(localDate)
            preferencesManager.putString(EXTRA_TO_DATE, toDate)
        }

        childFragmentManager.commit {
            replace(R.id.container, chooseFilterFragment)
        }

        binding.btnBack.setOnClickListener {
            if (currentScreen == CALENDAR_FRAGMENT_SCREEN) {
                currentScreen = CHOOSE_FILTER_SCREEN
                childFragmentManager.clearFragmentResult(STATE_SCREEN)
                preferencesManager.putString(EXTRA_FROM_DATE, EMPTY_TEXT)
                preferencesManager.putString(EXTRA_TO_DATE, EMPTY_TEXT)
                childFragmentManager.commit {
                    replace(R.id.container, ChooseFilterFragment.newInstance())
                }

            } else {
                preferencesManager.putString(STATE_SEGMENTS, EMPTY_TEXT)
                preferencesManager.putString(STATE_LANGUAGE, EMPTY_TEXT)
                preferencesManager.putString(EXTRA_FROM_DATE, EMPTY_TEXT)
                preferencesManager.putString(EXTRA_TO_DATE, EMPTY_TEXT)
                (requireActivity() as OnClickListenerFromFilterNews).clickListenerToHeadlinesFragment()
            }
        }

        binding.btnSuccess.setOnClickListener {
            if (currentScreen == CALENDAR_FRAGMENT_SCREEN) {
                currentScreen = CALENDAR_FRAGMENT_SCREEN
                childFragmentManager.commit {
                    replace(R.id.container, chooseFilterFragment)
                }
                childFragmentManager.setFragmentResult(
                    EXTRA_FROM,
                    bundleOf(EXTRA_DATA to fromDate)
                )
                childFragmentManager.setFragmentResult(
                    EXTRA_TO,
                    bundleOf(EXTRA_DATA to toDate)
                )
            } else {
                preferencesManager.putString(EXTRA_FROM_DATE, fromDate)
                preferencesManager.putString(EXTRA_TO_DATE, toDate)
                (requireActivity() as OnClickListenerFromFilterNews).clickListenerToFilterScreen()
            }
        }

//        requireActivity().onBackPressedDispatcher.addCallback(
//            viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//
//                    if (currentScreen != CHOOSE_FILTER_SCREEN) {
//                        childFragmentManager.commit {
//                            replace(R.id.container, calendarFragment)
//                        }
//                    }
//                }
//            }
//        )
    }

    interface OnClickListenerFromFilterNews {

        fun clickListenerToFilterScreen()

        fun clickListenerToHeadlinesFragment()
    }


    fun monthWithDayWithYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    companion object {
        const val CHOOSE_FILTER_SCREEN = "choose_filter"
        const val CALENDAR_FRAGMENT_SCREEN = "calendar_fragment"
        const val STATE_SCREEN = "state_screen"
        const val EXTRA_DATA = "data"
        const val EXTRA_FROM = "from"
        const val EXTRA_TO = "to"

        fun newInstance() = FilterNewsFragment()
    }

    override fun onClickListenerToChooseDate() {
        childFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container, CalendarFragment.newInstance())
        }
    }

    override fun clickListenerToChoose() {
        currentScreen = CALENDAR_FRAGMENT_SCREEN
        childFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container, chooseFilterFragment)
        }
        childFragmentManager.setFragmentResult(
            EXTRA_FROM,
            bundleOf(EXTRA_DATA to fromDate)
        )
        childFragmentManager.setFragmentResult(
            EXTRA_TO,
            bundleOf(EXTRA_DATA to toDate)
        )
    }
}