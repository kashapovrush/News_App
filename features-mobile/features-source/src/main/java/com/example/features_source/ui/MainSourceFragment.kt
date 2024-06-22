package com.example.features_source.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.features_source.R
import com.example.features_source.databinding.FragmentMainSourceBinding

class MainSourceFragment : Fragment(), SourcesFragment.ClickListenerFromSourcesFragment {

    private lateinit var binding: FragmentMainSourceBinding

    private var currentFragmentTag: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainSourceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("MainActivityTest", "start ${savedInstanceState?.getString("frag")}")


       if (savedInstanceState?.getString("frag") == SourcesFragment.TAG) {
            Log.d("MainActivityTest", "if ${SourcesFragment.TAG}")
            childFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_for_sources, SourcesFragment.newInstance())
                .commit()
        } else if (savedInstanceState?.getString("frag") == SourceNewsFragment.TAG) {
            Log.d("MainActivityTest", "if ${SourceNewsFragment.TAG}")
            childFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.container_for_sources,
                    SourceNewsFragment.newInstance(""),
                ).commit()
        } else {
           Log.d("MainActivityTest", "if else")
           childFragmentManager.beginTransaction()
               .addToBackStack(null)
               .replace(R.id.container_for_sources, SourcesFragment.newInstance())
               .commit()
       }

//        if (savedInstanceState == null) {
//            childFragmentManager.beginTransaction().replace(
//                R.id.container_for_sources,
//                SourcesFragment.newInstance(),
//                SourcesFragment.TAG
//            ).commit()
//        }
        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as ClickListenerFromMainSources).clickListenerToSearch()
        }

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as ClickListenerFromMainSources).clickListenerToFilter()
        }
    }


    companion object {
        fun newInstance() = MainSourceFragment()
    }

    interface ClickListenerFromMainSources {
        fun clickListenerToFilter()

        fun clickListenerToSearch()
    }

    override fun clickListenerToSourceNews(source: String?) {
        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(
                R.id.container_for_sources,
                SourceNewsFragment.newInstance(source ?: ""),
            ).addToBackStack(SourceNewsFragment.TAG).commit()
    }

}
