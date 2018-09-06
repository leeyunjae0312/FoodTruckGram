package org.androidtown.foodtruckgram.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidtown.foodtruckgram.Fragment.*;

/**
 * Created by 이예지 on 2018-09-06.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private MapFragment mapFragment = new MapFragment();
    private TruckListFragment truckListFragment = new TruckListFragment();
    private BookmarkFragment bookmarkFragment = new BookmarkFragment();
    private MyOrderFragment myOrderFragment = new MyOrderFragment();

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                return mapFragment;
            case 1:
                return truckListFragment;
            case 2:
                return myOrderFragment;
            case 3:
                return bookmarkFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
