package com.kashapovrush.headlines_features.presentation.ui.tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.dao.NewsHeadlinesDao;
import com.example.features_common.adapter.HeadlinesAdapter;
import com.example.features_common.mapper.NewsHeadlinesMapper;
import com.example.features_common.presenter.HeadlinesPresenter;
import com.example.features_common.presenter.HeadlinesView;
import com.kashapovrush.api.modelsDto.NewsHeadlines;
import com.kashapovrush.api.network.ApiService;
import com.kashapovrush.headlines_features.databinding.FragmentGeneralTabBinding;
import com.kashapovrush.headlines_features.di.HeadlinesComponentProvider;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import javax.inject.Inject;

public class SportTab extends MvpAppCompatFragment implements HeadlinesView{

    private FragmentGeneralTabBinding binding;
    private HeadlinesAdapter headlinesAdapter;
    @InjectPresenter
    HeadlinesPresenter presenter;
    @Inject
    ApiService apiService;

    @ProvidePresenter
    HeadlinesPresenter provideHeadlinesPresenter() {
        return new HeadlinesPresenter(apiService);
    }

    @Override
    public void onAttach(Context context) {
        ((HeadlinesComponentProvider) requireActivity().getApplication()).getHeadlinesComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGeneralTabBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerViewHeadlines();
        presenter.loadHeadlines(EXTRA_SPORT_TAB);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(true);
            presenter.updateHeadlines(EXTRA_SPORT_TAB);
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void setRecyclerViewHeadlines() {
        RecyclerView headlinesRV = binding.generalTabRv;
        headlinesAdapter = new HeadlinesAdapter(requireContext(), new HeadlinesAdapter.OnClickListenerHeadlinesAdapter() {
            @Override
            public void onClick(@NonNull NewsHeadlines post) {
                ((BusinessTab.ClickListener) requireActivity()).clickListenerFromTabs(post);
            }
        });
        headlinesRV.setAdapter(headlinesAdapter);

    }

    @Override
    public void loading() {
        binding.progressBarGeneral.setVisibility(View.VISIBLE);
    }

    @Override
    public void showResult(List<NewsHeadlines> result) {
        binding.progressBarGeneral.setVisibility(View.INVISIBLE);
        headlinesAdapter.submitList(result);
    }

    @Override
    public void updateHeadlines(List<NewsHeadlines> result) {
        binding.progressBarGeneral.setVisibility(View.INVISIBLE);
        headlinesAdapter.submitList(result);
    }

    @Override
    public void showError(String error) {
        binding.progressBarGeneral.setVisibility(View.INVISIBLE);
        ((GeneralTab.ClickListenerFromHeadlines) requireActivity()).clickListenerToShowError(error);
    }

    public static final String EXTRA_SPORT_TAB = "sport";

    public static SportTab newInstance() {
        return new SportTab();
    }

}