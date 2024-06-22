package com.example.features_source.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.headlines.HeadlinesAdapter
import com.example.features_common.viewmodel.CommonViewModel
import com.example.features_source.databinding.FragmentSourceNewsBinding
import com.example.features_source.di.SourceComponentProvider
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto
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

        savedInstanceState?.putString("frag", TAG)

//        fragmentManager?.saveBackStack(TAG)

        viewModel = ViewModelProvider(this, viewModelFactory)[CommonViewModel::class.java]
        val source = arguments?.getString(EXTRA_SOURCE)

        setRecyclerView()
        lifecycleScope.launch {
            if (source?.contains(" ") == true) {
                val new = source.replace(" ", "-")
                viewModel.getSourceNews(new)
            } else {
                viewModel.getSourceNews(source ?: "")
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.popBackStack()
                }

            })

        viewModel.headlinesList.observe(viewLifecycleOwner) {
            sourceNewsAdapter.submitList(it)
        }

        viewModel.loadingHeadlines.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.sourceNewsBar.visibility = View.VISIBLE
            } else {
                binding.sourceNewsBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun setRecyclerView() {
        sourceNewsAdapter = HeadlinesAdapter(
            requireContext(),
            object : HeadlinesAdapter.OnClickListenerHeadlinesAdapter {
                override fun onClick(post: NewsHeadlines) {
                    (requireActivity() as ClickListenerFromSourceNews).clickListenerToNewsPost(post)
                }

            })
        with(binding.sourceNewsRv) {
            adapter = sourceNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    interface ClickListenerFromSourceNews {
        fun clickListenerToNewsPost(post: NewsHeadlines?)
    }

    companion object {

        const val EXTRA_SOURCE = "source"

        const val TAG = "SourceNewsFragment"

        fun newInstance(source: String): SourceNewsFragment {
            return SourceNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SOURCE, source)
                }
            }
        }
    }
}