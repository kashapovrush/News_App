package com.example.features_filter_news.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.example.features_filter_news.R
import com.example.features_filter_news.adapter.year_adapter.YearChooseAdapter
import com.example.features_filter_news.databinding.FragmentEditYearBinding
import com.example.features_filter_news.model.Year
import java.time.LocalDate

class EditYearFragment : Fragment() {

    private lateinit var binding: FragmentEditYearBinding
    private lateinit var yearChooseAdapter: YearChooseAdapter
    private var lastEnabledYear = Year.DISABLED

    private var fixList: MutableList<Year> =
        mutableListOf(
            Year("2019"),
            Year("2020"),
            Year("2021"),
            Year("2022"),
            Year("2023"),
            Year("2024"),
            Year("2025"),
            Year("2026"),
            Year("2027"),
            Year("2028"),
            Year("2029"),
            Year("2030"),
            Year("2031"),
            Year("2032"),
            Year("2033")
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditYearBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragment?.clearFragmentResultListener(CalendarFragment.EXTRA_YEAR)
        setList()
    }

    private fun setList() {
        val newList = mutableListOf<Year>()
        newList.addAll(fixList)
        yearChooseAdapter =
            YearChooseAdapter(newList, object : YearChooseAdapter.OnItemClickListener {

                override fun setEnabledState(position: Int, year: Year) {
                    newList.clear()
                    newList.addAll(fixList)
                    val view = binding.yearsRecyclerView.layoutManager?.findViewByPosition(lastEnabledYear)
                    val text = view?.findViewById<TextView>(R.id.year_cell)
                    text?.setTextColor(Color.BLACK)
                    binding.yearsRecyclerView.getChildAt(lastEnabledYear).setBackgroundResource(R.drawable.background_disabled_year)
                    lastEnabledYear = position
                    binding.yearsRecyclerView.getChildAt(position)
                        .setBackgroundResource(R.drawable.background_enabled_year)
                    val new = year.copy(isActive = Year.ENABLED)
                    newList.remove(year)
                    newList.add(position, new)

                    var localDate = LocalDate.now()
                    localDate = localDate.withYear(year.value.toInt())
                    localDate = localDate.withMonth(1)

                    val wrapper = LocalDateParcelable(localDate)
                    parentFragment?.setFragmentResult(
                        CalendarFragment.EXTRA_YEAR,
                        bundleOf(EditDateFragment.EXTRA_DATE to wrapper)
                    )
                }
            })

        with(binding.yearsRecyclerView) {
            adapter = yearChooseAdapter
            setLayoutManager(GridLayoutManager(requireContext(), 3))
        }

        yearChooseAdapter.submitList(newList)
    }

    companion object {
        fun newInstance() = EditYearFragment()
    }
}