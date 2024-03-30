package com.kashapovrush.headlines_features.presentation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import moxy.MvpAppCompatFragment;

public class CategoryAdapter extends FragmentStateAdapter {
    private List<MvpAppCompatFragment> fragmentsList;

    public CategoryAdapter(Fragment fragment, List<MvpAppCompatFragment> fragmentsList) {
        super(fragment);
        this.fragmentsList = fragmentsList;
    }

    @Override
    public int getItemCount() {
        return fragmentsList.size();
    }


    @Override
    public Fragment createFragment(int position) {
        return fragmentsList.get(position);
    }
}