package com.example.features_search.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.HeadlinesAdapter
import com.example.features_common.state.ViewIntent
import com.example.features_common.viewmodel.CommonViewModel
import com.example.features_search.databinding.FragmentSearchHeadlinesBinding
import com.example.features_search.di.SearchHeadlinesComponentProvider
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import javax.inject.Inject

class SearchHeadlinesFragment : Fragment() {

    private lateinit var binding: FragmentSearchHeadlinesBinding
    private lateinit var adapter: HeadlinesAdapter
    private var searchQuery = ""

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as SearchHeadlinesComponentProvider).getSearchHeadlinesComponent()
            .inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[CommonViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchHeadlinesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        viewModel.loadingHeadlines.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.searchProgressBar.visibility = View.VISIBLE
            } else {
                binding.searchProgressBar.visibility = View.INVISIBLE
            }
        }

        binding.btnHideSearch.setOnClickListener {
            (requireActivity() as OnClickListenerFromSearchHeadlinesFragment).fromSearchToHeadlines()
        }

        binding.searchHeadlines.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchQuery = query ?: ""
                viewModel.searchHeadlines(query ?: EMPTY_TEXT)


                viewModel.headlinesList.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.btnClearTextInSearchView.setOnClickListener {
            binding.searchHeadlines.setQuery(EMPTY_TEXT, false)
        }

        binding.searchSwipeRefresh.setOnRefreshListener {
            binding.searchSwipeRefresh.isRefreshing = true
            viewModel.searchHeadlines(searchQuery)
            binding.searchSwipeRefresh.isRefreshing = false
        }
    }


    fun setRecyclerView() {
        adapter = HeadlinesAdapter(
            requireContext(),
            object : HeadlinesAdapter.OnClickListenerHeadlinesAdapter {
                override fun onClick(post: NewsHeadlines) {
                    (requireActivity() as OnClickListenerFromSearchHeadlinesFragment).clickListenerToNewsPost(
                        post
                    )
                }

            })
        binding.newsPostsRv.adapter = adapter
        binding.newsPostsRv.layoutManager = LinearLayoutManager(requireContext())


    }


    interface OnClickListenerFromSearchHeadlinesFragment {

        fun clickListenerToNewsPost(post: NewsHeadlines)

        fun fromSearchToHeadlines()
    }

    companion object {

        const val EMPTY_TEXT = ""

        fun newInstance() = SearchHeadlinesFragment()
    }
}