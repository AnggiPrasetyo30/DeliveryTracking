package com.example.monitorbudimas.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPager extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    protected List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentListFaktur = new ArrayList<>();

    public ViewPager(FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        stringList.add(title);
    }

    public void muatFragment(Fragment fragment) {
        fragmentListFaktur.add(fragment);
    }
    public void removeFragment(Fragment fragment, String title){
        fragmentList.remove(fragment);
        stringList.remove(title);
    }
}

