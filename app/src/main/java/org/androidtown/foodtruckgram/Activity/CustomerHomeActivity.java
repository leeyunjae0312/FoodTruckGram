package org.androidtown.foodtruckgram.Activity;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidtown.foodtruckgram.Adapter.ViewPagerAdapter;
import org.androidtown.foodtruckgram.Fragment.BlankFragment;
import org.androidtown.foodtruckgram.Fragment.BookmarkFragment;
import org.androidtown.foodtruckgram.Fragment.MapFragment;
import org.androidtown.foodtruckgram.Fragment.MyOrderFragment;
import org.androidtown.foodtruckgram.Fragment.TruckListFragment;
import org.androidtown.foodtruckgram.R;

import java.util.Map;

public class CustomerHomeActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    BottomNavigationView bottomNavigationView;

    BlankFragment blankFragment;
    BookmarkFragment bookmarkFragment;
    MapFragment mapFramgment;
    MyOrderFragment myOrderFragment;
    TruckListFragment truckListFragment;

    int currentMenu;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        currentMenu = R.id.navigation_home;
        setupViewPager(viewPager);
        prevMenuItem = bottomNavigationView.getMenu().getItem(0);


//        fragment = mapFramgment;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentMenu = R.id.navigation_home;
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_list:
                        currentMenu = R.id.navigation_list;;
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_order:
                        currentMenu = R.id.navigation_order;
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_bookmark:
                        currentMenu = R.id.navigation_bookmark;
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


   viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            currentMenu = bottomNavigationView.getMenu().getItem(position).getItemId();
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    });
}

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),3);

        blankFragment = new BlankFragment();
        mapFramgment = new MapFragment();
        truckListFragment = new TruckListFragment();
        myOrderFragment = new MyOrderFragment();
        bookmarkFragment = new BookmarkFragment();

        viewPagerAdapter.addFragment(mapFramgment); // home
        viewPagerAdapter.addFragment(truckListFragment);
        viewPagerAdapter.addFragment(myOrderFragment);
        viewPagerAdapter.addFragment(bookmarkFragment);


        viewPager.setAdapter(viewPagerAdapter);
    }
}

