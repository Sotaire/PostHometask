package com.example.posthometask.fragmentA;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapters extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public FragmentAdapters(ArrayList<Fragment> fragments, @NonNull FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragmentArrayList = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

}
