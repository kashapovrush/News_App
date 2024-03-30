package com.kashapovrush.headlines_features.presentation.ui;

import static androidx.core.os.BundleKt.bundleOf;
import static com.example.features_common.constans.Constants.CURRENT_STATE;
import static com.example.features_common.constans.Constants.EXTRA_HEADLINES_STATE;
import static com.example.features_common.constans.Constants.NAVIGATION_STATE;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.kashapovrush.headlines_features.R;
import com.kashapovrush.headlines_features.databinding.FragmentHeadlinesBinding;

public class HeadlinesFragment extends Fragment {
    private FragmentHeadlinesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHeadlinesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_STATE, EXTRA_HEADLINES_STATE);
        requireActivity().getSupportFragmentManager().setFragmentResult(
                NAVIGATION_STATE,
                bundle
        );
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        transaction.replace(R.id.container, TabCategoryFragment.newInstance());
        transaction.commit();
        binding.btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnClickListenerFromHeadlinesFragment) requireActivity()).fromHeadlinesToSearch();
            }
        });
        binding.btnStartFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnClickListenerFromHeadlinesFragment) requireActivity()).clickListenerToFilter();
            }
        });
    }

    public interface OnClickListenerFromHeadlinesFragment {
        void clickListenerToFilter();
        void fromHeadlinesToSearch();
    }

    public static HeadlinesFragment newInstance() {
        return new HeadlinesFragment();
    }
}
