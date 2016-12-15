package com.android.omvinayaga.smalltalkkannada;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Manjunath on 12/14/2016.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ColorsFragment();
            case 1:
                return new PhrasesFragment();
            case 2:
                return new NumbersFragment();
            case 3:
                return new FamilyFragment();
            case 4:
                return new AnatomyFragment();
            case 5:
                return new DaysFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "COL";
            case 1:
                return "PHR";
            case 2:
                return "NUM";
            case 3:
                return "FAM";
            case 4:
                return "BOD";
            case 5:
                return "DAY";
            default:
                return null;
        }
    }
}

