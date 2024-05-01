package com.example.features_source.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.headlines.SourcesAdapter
import com.example.features_common.constans.Constants.CURRENT_STATE
import com.example.features_common.constans.Constants.EXTRA_SOURCE_STATE
import com.example.features_common.constans.Constants.NAVIGATION_STATE
import com.example.features_common.viewmodel.SourcesViewModel
import com.example.features_source.databinding.FragmentSourcesBinding
import com.example.features_source.di.SourceComponentProvider
import com.example.prefrences.PreferencesManager
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class SourcesFragment : Fragment() {

    private lateinit var binding: FragmentSourcesBinding
    private lateinit var sourcesAdapter: SourcesAdapter
    private lateinit var viewModel: SourcesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var preferences: PreferencesManager

    override fun onAttach(context: Context) {
        (requireActivity().application as SourceComponentProvider).getSourceComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SourcesViewModel::class.java]
        val time = preferences.getLong(EXTRA_CURRENT_TIME)

        if (time != 0L && System.currentTimeMillis() - time > 60000) {
            lifecycleScope.launch {
                viewModel.clearCached()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSourcesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        lifecycleScope.launch {

            viewModel.cachedSources.collectLatest {
                preferences.putLong(EXTRA_CURRENT_TIME, System.currentTimeMillis())
                sourcesAdapter.submitList(it)
            }
        }
        viewModel.sources.observe(viewLifecycleOwner) {
            sourcesAdapter.submitList(it)
        }


        Log.d("MainActivityTest", "${preferences.getLong(EXTRA_CURRENT_TIME)}")

        viewModel.loadingHeadlines.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.sourcesBar.visibility = View.VISIBLE
            } else {
                binding.sourcesBar.visibility = View.INVISIBLE
            }
        }

        sourcesAdapter.onCLickListenerItem = {
            (requireActivity() as ClickListenerFromSourcesFragment).clickListenerToSourceNews(it.name)
        }

        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as ClickListenerFromSourcesFragment).clickListenerToSearch()
        }

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as ClickListenerFromSourcesFragment).clickListenerToFilter()
        }

        requireActivity().supportFragmentManager.setFragmentResult(
            NAVIGATION_STATE,
            bundleOf(CURRENT_STATE to EXTRA_SOURCE_STATE)
        )
    }


    private fun setRecyclerView() {
        sourcesAdapter = SourcesAdapter(requireContext())
        with(binding.sourcesRv) {
            adapter = sourcesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    interface ClickListenerFromSourcesFragment {

        fun clickListenerToSourceNews(source: String?)

        fun clickListenerToFilter()

        fun clickListenerToSearch()

        fun clickListenerToNewsPost(post: NewsHeadlines?)
    }

    companion object {

        const val EXTRA_CURRENT_TIME = "current_time"
        fun newInstance() = SourcesFragment()
    }
}