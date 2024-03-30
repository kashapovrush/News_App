package com.example.features_filter_news.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.features_filter_news.R
import com.example.features_filter_news.databinding.FragmentChooseFilterBinding
import com.example.features_filter_news.di.FilterNewsComponentProvider
import com.example.features_filter_news.ui.CalendarFragment.Companion.EMPTY_TEXT
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.CHOOSE_FILTER_SCREEN
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.EXTRA_DATA
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.EXTRA_FROM
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.EXTRA_TO
import com.example.features_filter_news.ui.FilterNewsFragment.Companion.STATE_SCREEN
import com.example.prefrences.PreferencesManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class ChooseFilterFragment : Fragment() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    private lateinit var binding: FragmentChooseFilterBinding
    private var fromDate: String? = ""
    private var toDate: String? = ""

    override fun onAttach(context: Context) {
        (requireActivity().application as FilterNewsComponentProvider).getFilterNewsComponentProvider()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragment?.setFragmentResult(
            STATE_SCREEN,
            bundleOf(EXTRA_DATA to CHOOSE_FILTER_SCREEN)
        )

        setFilterSegment()
        setFilterLanguage()
        showTextPeriod()

        binding.layoutPopularFilter.setOnClickListener {
            preferencesManager.putString(STATE_SEGMENTS, EXTRA_POPULAR)
            setFilterSegment()
        }

        binding.layoutNewFilter.setOnClickListener {
            preferencesManager.putString(STATE_SEGMENTS, EXTRA_NEW)
            setFilterSegment()
        }

        binding.layoutRelevantFilter.setOnClickListener {
            preferencesManager.putString(STATE_SEGMENTS, EXTRA_RELEVANT)
            setFilterSegment()
        }

        binding.languageEnglish.setOnClickListener {
            preferencesManager.putString(STATE_LANGUAGE, EXTRA_ENGLISH)
            setFilterLanguage()
        }

        binding.languageDeutsch.setOnClickListener {
            preferencesManager.putString(STATE_LANGUAGE, EXTRA_DEUTSCH)
            setFilterLanguage()
        }

        binding.languageRussian.setOnClickListener {
            preferencesManager.putString(STATE_LANGUAGE, EXTRA_RUSSIAN)
            setFilterLanguage()
        }


        binding.btnChooseDate.setOnClickListener {
            (requireParentFragment() as OnClickListenerFromChooseFilterFragment).onClickListenerToChooseDate()
        }

        childFragmentManager.clearFragmentResultListener(EXTRA_FROM)
        setFragmentResultListener(EXTRA_FROM) { _, bundle ->
            fromDate = bundle.getString(EXTRA_DATA)
            showTextPeriod()
        }

        childFragmentManager.clearFragmentResultListener(EXTRA_TO)
        setFragmentResultListener(EXTRA_TO) { _, bundle ->
            toDate = bundle.getString(EXTRA_DATA)
            showTextPeriod()
        }

//        setFragmentResultListener(EXTRA_RESULT_DAYS) { _, bundle ->
//            toDate = bundle.getString(EXTRA_DATA)
//
//            showTextPeriod()
//            binding.btnChooseDate.setImageResource(R.drawable.enabled_filter_date)
//        }
    }

    private fun showTextPeriod() {
        val from = preferencesManager.getString(EXTRA_FROM_DATE)
        val to = preferencesManager.getString(EXTRA_TO_DATE)


        if (from != "" && from != null) {
            binding.btnChooseDate.setImageResource(R.drawable.enabled_filter_date)
        }

        if (to != "" && to != null) {
            binding.btnChooseDate.setImageResource(R.drawable.enabled_filter_date)
        }

        if (to != null && to != "") {
            binding.textShowPeriod.text = "$from-$to"
        } else if (from != null) {
            binding.textShowPeriod.text = from

        } else {
            binding.textShowPeriod.text = EMPTY_TEXT
        }
    }

    fun monthWithDayWithYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    private fun setFilterLanguage() {
        when (preferencesManager.getString(STATE_LANGUAGE)) {
            EXTRA_RUSSIAN -> {
                clearBackgroundLanguage()
                binding.languageRussian.setBackgroundResource(R.drawable.background_select_language_enabled)
            }

            EXTRA_ENGLISH -> {
                clearBackgroundLanguage()
                binding.languageEnglish.setBackgroundResource(R.drawable.background_select_language_enabled)
            }

            EXTRA_DEUTSCH -> {
                clearBackgroundLanguage()
                binding.languageDeutsch.setBackgroundResource(R.drawable.background_select_language_enabled)
            }

            else -> {
                clearBackgroundLanguage()
            }
        }
    }

    private fun setFilterSegment() {
        when (preferencesManager.getString(STATE_SEGMENTS)) {
            EXTRA_POPULAR -> {
                clearBackgroundSegments()
                with(binding) {
                    layoutPopularFilter.setBackgroundResource(R.drawable.background_segments_left_button_enabled)
                    iconNewFilter.visibility = View.GONE
                    iconPopularFilter.visibility = View.VISIBLE
                    iconRelevantFilter.visibility = View.GONE
                }

            }

            EXTRA_NEW -> {
                clearBackgroundSegments()
                with(binding) {
                    layoutNewFilter.setBackgroundResource(R.drawable.background_segments_center_button_enabled)
                    iconNewFilter.visibility = View.VISIBLE
                    iconPopularFilter.visibility = View.GONE
                    iconRelevantFilter.visibility = View.GONE
                }

            }

            EXTRA_RELEVANT -> {
                clearBackgroundSegments()
                with(binding) {
                    layoutRelevantFilter.setBackgroundResource(R.drawable.background_segments_right_button_enabled)
                    iconNewFilter.visibility = View.GONE
                    iconPopularFilter.visibility = View.GONE
                    iconRelevantFilter.visibility = View.VISIBLE
                }
            }

            else -> {
                clearBackgroundSegments()
            }
        }
    }

    fun clearBackgroundSegments() {
        with(binding) {
            layoutNewFilter.setBackgroundResource(R.drawable.background_segments_center_button_disabled)
            layoutPopularFilter.setBackgroundResource(R.drawable.background_segments_left_button_disabled)
            layoutRelevantFilter.setBackgroundResource(R.drawable.background_segments_right_button_disabled)
        }

    }

    fun clearBackgroundLanguage() {
        with(binding) {
            languageEnglish.setBackgroundResource(R.drawable.background_select_language_disabled)
            languageDeutsch.setBackgroundResource(R.drawable.background_select_language_disabled)
            languageRussian.setBackgroundResource(R.drawable.background_select_language_disabled)
        }

    }


    interface OnClickListenerFromChooseFilterFragment {
        fun onClickListenerToChooseDate()
    }

    companion object {

        const val STATE_SEGMENTS = "state_segments"
        const val EXTRA_POPULAR = "popularity"
        const val EXTRA_NEW = "publishedAt"
        const val EXTRA_RELEVANT = "relevancy"

        const val STATE_LANGUAGE = "state_language"
        const val EXTRA_RUSSIAN = "ru"
        const val EXTRA_ENGLISH = "en"
        const val EXTRA_DEUTSCH = "de"

        const val EXTRA_FROM_DATE = "from_date"
        const val EXTRA_TO_DATE = "to_date"

        fun newInstance() = ChooseFilterFragment()
    }
}