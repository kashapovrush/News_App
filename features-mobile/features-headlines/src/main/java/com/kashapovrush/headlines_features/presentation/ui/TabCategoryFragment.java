package com.kashapovrush.headlines_features.presentation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kashapovrush.headlines_features.R;
import com.kashapovrush.headlines_features.databinding.FragmentTabCategoryBinding;
import com.kashapovrush.headlines_features.presentation.adapter.CategoryAdapter;
import com.kashapovrush.headlines_features.presentation.ui.tabs.BusinessTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.EntertainmentTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.GeneralTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.HealthTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.ScienceTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.SportTab;
import com.kashapovrush.headlines_features.presentation.ui.tabs.TechnologyTab;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;

public class TabCategoryFragment extends Fragment {
    private FragmentTabCategoryBinding binding;
    private List<MvpAppCompatFragment> listTabs;
    private List<Integer> listTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabCategoryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listTabs = new ArrayList<>();
        listTabs.add(GeneralTab.newInstance());
        listTabs.add(EntertainmentTab.newInstance());
        listTabs.add(BusinessTab.newInstance());
        listTabs.add(HealthTab.newInstance());
        listTabs.add(ScienceTab.newInstance());
        listTabs.add(SportTab.newInstance());
        listTabs.add(TechnologyTab.newInstance());

        listTitle = new ArrayList<>();
        listTitle.add(R.layout.general_category_tab);
        listTitle.add(R.layout.enterteiment_category_tab);
        listTitle.add(R.layout.business_category_tab);
        listTitle.add(R.layout.health_category_tab);
        listTitle.add(R.layout.science_category_tab);
        listTitle.add(R.layout.sports_category_tab);
        listTitle.add(R.layout.technology_category_tab);

        CategoryAdapter adapter = new CategoryAdapter(this, listTabs);
        binding.containerViewPager.setAdapter(adapter);
        binding.containerViewPager.setOffscreenPageLimit(1);

        TabLayoutMediator mediator = new TabLayoutMediator(binding.categoryTableLayout, binding.containerViewPager,
                (tab, position) -> tab.setCustomView(listTitle.get(position)));
        mediator.attach();
    }

    public static TabCategoryFragment newInstance() {
        return new TabCategoryFragment();
    }
}