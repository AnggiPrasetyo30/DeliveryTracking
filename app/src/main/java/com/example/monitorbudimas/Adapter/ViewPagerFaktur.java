package com.example.monitorbudimas.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFaktur extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentListFaktur = new ArrayList<>();

    public ViewPagerFaktur(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentListFaktur.get(position);
    }


    @Override
    public int getCount() {
        return fragmentListFaktur.size();
    }

    public void muatFragment(Fragment fragment) {
        fragmentListFaktur.add(fragment);
    }

}
