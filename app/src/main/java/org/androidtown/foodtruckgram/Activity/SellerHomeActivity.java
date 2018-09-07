package org.androidtown.foodtruckgram.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidtown.foodtruckgram.Adapter.ViewPagerAdapter;
import org.androidtown.foodtruckgram.Fragment.*;
import org.androidtown.foodtruckgram.Fragment.SellerFragment.MenuFragment;
import org.androidtown.foodtruckgram.Fragment.SellerFragment.OpenCloseFragment;
import org.androidtown.foodtruckgram.Fragment.SellerFragment.OrderListFragment;
import org.androidtown.foodtruckgram.Fragment.SellerFragment.ReviewFragment;
import org.androidtown.foodtruckgram.R;

public class SellerHomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    BottomNavigationView bottomNavigationView;

    OpenCloseFragment openCloseFragment;
    OrderListFragment orderListFragment;
    MenuFragment menuFragment;
    ReviewFragment reviewFragment;


    int currentMenu;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        viewPager = (ViewPager) findViewById(R.id.viewPager_seller);
        viewPager.setOffscreenPageLimit(4);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_seller);

        currentMenu = R.id.navigation_open;
        setupViewPager(viewPager);
        prevMenuItem = bottomNavigationView.getMenu().getItem(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_open:
                        currentMenu = R.id.navigation_open;
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_order_seller:
                        currentMenu = R.id.navigation_order_seller;;
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_menu:
                        currentMenu = R.id.navigation_menu;
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_review:
                        currentMenu = R.id.navigation_review;
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
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 4);

        openCloseFragment = new OpenCloseFragment();
        orderListFragment = new OrderListFragment();
        menuFragment = new MenuFragment();
        reviewFragment = new ReviewFragment();

        viewPagerAdapter.addFragment(openCloseFragment); // home
        viewPagerAdapter.addFragment(orderListFragment);
        viewPagerAdapter.addFragment(menuFragment);
        viewPagerAdapter.addFragment(reviewFragment);

        viewPager.setAdapter(viewPagerAdapter);
    }

}
