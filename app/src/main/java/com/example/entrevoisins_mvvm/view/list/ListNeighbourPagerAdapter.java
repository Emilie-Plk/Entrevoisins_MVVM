package com.example.entrevoisins_mvvm.view.list;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.entrevoisins_mvvm.R;

public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_neighbour_title, R.string.tab_favorites_title};

    @NonNull
    private final Context context;

    public ListNeighbourPagerAdapter(
        @NonNull Context context,
        @NonNull FragmentManager fm
    ) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NeighbourListFragment.newInstance(false);
        } else if (position == 1) {
            return NeighbourListFragment.newInstance(true);
        } else
            throw new IllegalArgumentException("Invalid position: " + position);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }
}
