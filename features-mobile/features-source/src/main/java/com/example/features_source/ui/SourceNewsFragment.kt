package com.example.features_source.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.HeadlinesAdapter
import com.example.features_common.viewmodel.CommonViewModel
import com.example.features_source.R
import com.example.features_source.databinding.FragmentSourceNewsBinding
import com.example.features_source.di.SourceComponentProvider
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SourceNewsFragment : Fragment() {

    private lateinit var binding: FragmentSourceNewsBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var sourceNewsAdapter: HeadlinesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        (requireActivity().application as SourceComponentProvider).getSourceComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSourceNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommonViewModel::class.java]
        val source = arguments?.getString(EXTRA_SOURCE)

        binding.titleSource.text = source

        setRecyclerView()
        CoroutineScope(Dispatchers.IO).launch {
            if (source?.contains(" ") == true) {
                val new = source.replace(" ", "-")
                viewModel.getSourceNews(new)
            } else {
                viewModel.getSourceNews(source ?: "")
            }

        }

        viewModel.headlinesList.observe(viewLifecycleOwner) {
            sourceNewsAdapter.submitList(it)
        }

        viewModel.loadingHeadlines.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) {
                binding.sourceNewsBar.visibility = View.VISIBLE
            } else {
                binding.sourceNewsBar.visibility = View.INVISIBLE
            }
        }

        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as SourcesFragment.ClickListenerFromSourcesFragment).clickListenerToSearch()
        }

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as SourcesFragment.ClickListenerFromSourcesFragment).clickListenerToFilter()
        }

    }

    private fun setRecyclerView() {
        sourceNewsAdapter = HeadlinesAdapter(requireContext(), object : HeadlinesAdapter.OnClickListenerHeadlinesAdapter{
            override fun onClick(post: NewsHeadlines) {
                (requireActivity() as SourcesFragment.ClickListenerFromSourcesFragment).clickListenerToNewsPost(post)
            }

        })
        with(binding.sourceNewsRv) {
            adapter = sourceNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {

        const val EXTRA_SOURCE = "source"

        fun newInstance(source: String): SourceNewsFragment {
            return SourceNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SOURCE, source)
                }
            }
        }
    }
}