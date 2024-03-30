package com.example.features_filter_news.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.HeadlinesAdapter
import com.example.features_common.state.ViewIntent
import com.example.features_common.state.ViewState
import com.example.features_common.viewmodel.CommonViewModel
import com.example.features_filter_news.databinding.FragmentFilteredNewsBinding
import com.example.features_filter_news.di.FilterNewsComponentProvider
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.STATE_LANGUAGE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.EXTRA_FROM_DATE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.EXTRA_TO_DATE
import com.example.features_filter_news.ui.ChooseFilterFragment.Companion.STATE_SEGMENTS
import com.example.prefrences.PreferencesManager
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilteredNewsFragment : Fragment() {

    private lateinit var binding: FragmentFilteredNewsBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var filteredNewsAdapter: HeadlinesAdapter
    private var countFilter = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onAttach(context: Context) {
        (requireActivity().application as FilterNewsComponentProvider).getFilterNewsComponentProvider()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommonViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilteredNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        if (preferencesManager.getString(STATE_LANGUAGE) != null && preferencesManager.getString(STATE_LANGUAGE) != "") {
            countFilter++
        }

        if (preferencesManager.getString(EXTRA_FROM_DATE) != null && preferencesManager.getString(EXTRA_FROM_DATE) != "") {
            countFilter++
        }

        if (preferencesManager.getString(STATE_SEGMENTS) != null && preferencesManager.getString(STATE_SEGMENTS) != "") {
            countFilter++
        }

        binding.countFilters.text = countFilter.toString()

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as ClickListenerFromFilteredNews).clickListenerToChooseFilter()
        }

        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as ClickListenerFromFilteredNews).clickListenerToSearch()
        }

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.requestIntent(
                ViewIntent.RequestData,
                preferencesManager.getString(STATE_LANGUAGE) ?: "",
                from = preferencesManager.getString(EXTRA_FROM_DATE) ?: "",
                to = preferencesManager.getString(EXTRA_TO_DATE) ?: "",
                sortBy = preferencesManager.getString(STATE_SEGMENTS) ?: ""
            )
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ViewState.Loading -> {
                    binding.filteredNewsProgressBar.visibility = View.VISIBLE
                }
                is ViewState.Error -> {
                    binding.filteredNewsProgressBar.visibility = View.INVISIBLE
                    (requireActivity() as ClickListenerFromFilteredNews).clickListenerToShowError(state.error)
                }
                is ViewState.Success -> {
                    binding.filteredNewsProgressBar.visibility = View.INVISIBLE
                    filteredNewsAdapter.submitList(state.result)
                }
            }
        }

        binding.filterSwipeRefresh.setOnRefreshListener {
            binding.filterSwipeRefresh.isRefreshing = true
            viewModel.requestIntent(
                ViewIntent.UpdateList,
                preferencesManager.getString(STATE_LANGUAGE) ?: "",
                from = preferencesManager.getString(EXTRA_FROM_DATE) ?: "",
                to = preferencesManager.getString(EXTRA_TO_DATE) ?: "",
                sortBy = preferencesManager.getString(STATE_SEGMENTS) ?: ""
            )
            binding.filterSwipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        countFilter = 0
    }

    private fun setRecyclerView() {
        filteredNewsAdapter = HeadlinesAdapter(requireContext(), object : HeadlinesAdapter.OnClickListenerHeadlinesAdapter{
            override fun onClick(post: NewsHeadlines) {
                (requireActivity() as ClickListenerFromFilteredNews).clickListenerToNewsPost(post)
            }

        })
        with(binding.filteredNewsRv) {
            adapter = filteredNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    interface ClickListenerFromFilteredNews {

        fun clickListenerToNewsPost(post: NewsHeadlines)

        fun clickListenerToChooseFilter()

        fun clickListenerToSearch()

        fun clickListenerToShowError(error: String)
    }

    companion object {
        fun newInstance() = FilteredNewsFragment()
    }
}