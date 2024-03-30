package com.example.features_favourite.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.features_common.adapter.HeadlinesAdapter
import com.example.features_common.constans.Constants.CURRENT_STATE
import com.example.features_common.constans.Constants.EXTRA_FAVOURITE_STATE
import com.example.features_common.constans.Constants.NAVIGATION_STATE
import com.example.features_common.viewmodel.FavouriteViewModel
import com.example.features_favourite.databinding.FragmentFavouriteBinding
import com.example.features_favourite.di.FavouriteComponentProvider
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import javax.inject.Inject

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var listAdapter: HeadlinesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        (requireActivity().application as FavouriteComponentProvider).getFavouriteComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager.setFragmentResult(
            NAVIGATION_STATE,
            bundleOf(CURRENT_STATE to EXTRA_FAVOURITE_STATE)
        )

        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        listAdapter = HeadlinesAdapter(requireContext(), object : HeadlinesAdapter.OnClickListenerHeadlinesAdapter{
            override fun onClick(post: NewsHeadlines) {
                (requireActivity() as ClickListenerFromFavourite).clickListenerToNewsPost(post)
            }

        })
        binding.favouritesRv.adapter = listAdapter
        binding.favouritesRv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.newsList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as ClickListenerFromFavourite).clickListenerToSearch()
        }

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as ClickListenerFromFavourite).clickListenerToFilter()
        }

    }

    interface ClickListenerFromFavourite {

        fun clickListenerToNewsPost(post: NewsHeadlines)

        fun clickListenerToSearch()

        fun clickListenerToFilter()
    }

    companion object {
        fun newInstance() = FavouriteFragment()
    }
}