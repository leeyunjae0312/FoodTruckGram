package org.androidtown.foodtruckgram.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import org.androidtown.foodtruckgram.Adapter.TabPagerAdapter;
import org.androidtown.foodtruckgram.R;

public class CustomerHomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);


        //FrameLayout frameLayout = (FrameLayout)findViewById(R.id.fragment);

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,tmpFragment);
        fragmentTransaction.commit();*/

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("FoodTruck"));
        tabLayout.addTab(tabLayout.newTab().setText("Order"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.selectedTextColor));
        //tabLayout.setTabTextColors(R.color.mainTextColor,getResources().getColor(R.color.selectedTextColor));

        // Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Creating TabPagerAdapter adapter
        //pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), 0);

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter); //???????
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
