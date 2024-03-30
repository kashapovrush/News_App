package com.example.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.error.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val errorText = arguments?.getString(EXTRA_ERROR)

        binding.textError.text = errorText

        binding.btnStartFilter.setOnClickListener {
            (requireActivity() as ClickListenerFromError).clickListenerToFilter()
        }

        binding.btnStartSearch.setOnClickListener {
            (requireActivity() as ClickListenerFromError).clickListenerToSearch()
        }
    }

    interface ClickListenerFromError {

        fun clickListenerToSearch()

        fun clickListenerToFilter()
    }

    companion object {

        const val EXTRA_ERROR = "error"

        fun newInstance(error: String): ErrorFragment {
            return ErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_ERROR, error)
                }
            }
        }
    }
}