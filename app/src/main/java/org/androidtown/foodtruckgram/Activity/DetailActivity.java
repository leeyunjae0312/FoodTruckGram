package org.androidtown.foodtruckgram.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidtown.foodtruckgram.Adapter.ViewPagerAdapter;
import org.androidtown.foodtruckgram.Fragment.CustomerReviewFragment;
import org.androidtown.foodtruckgram.Fragment.OrderFragment;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.ReviewInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private CustomerReviewFragment reviewFragment = new CustomerReviewFragment();
    private OrderFragment orderFragment = new OrderFragment();

    private int currentMenu;
    private MenuItem prevMenuItem;

    private UserInfo userInfo = UserInfo.getUserInfo();
    private List<FoodTruckInfo> foodTruckInfos;
    private String serverURL_getFoodTruckInfoList = "http://" + HttpClient.ipAdress + ":8080" + HttpClient.urlBase + "/c/getReview";
    private FoodTruckDB foodTruckDB;

    private TabLayout tabLayout;
    private ActionBar actionBar;
    FoodTruckInfo foodTruckInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
/*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.hideOverflowMenu();
*/

        actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        foodTruckInfo = (FoodTruckInfo) intent.getSerializableExtra("foodtruckInfo");

        Bundle bundle = new Bundle();
        bundle.putSerializable("foodTruckInfo",(Serializable) foodTruckInfos);
        reviewFragment.setArguments(bundle);


        foodTruckDB = new FoodTruckDB();
        Map<String, String> params = new HashMap<String, String>();
        params.put("storeName", foodTruckInfo.getStoreName());
        foodTruckDB.execute(params);




    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 2);


       /* Bundle bundle = new Bundle();
        bundle.putSerializable("foodTruckInfos",(Serializable) foodTruckInfos);
        truckListFragment.setArguments(bundle);
*/
        viewPagerAdapter.addFragment(orderFragment);
        viewPagerAdapter.addFragment(reviewFragment);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    class FoodTruckDB extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>...maps) {

            HttpClient.Builder http = new HttpClient.Builder("POST", serverURL_getFoodTruckInfoList);
            http.addAllParameters(maps[0]);

            HttpClient post = http.create();
            post.request();

            int statusCode = post.getHttpStatusCode();

            Log.i("yunjae", "응답코드"+statusCode);

            String body = post.getBody();

            Log.i("yunjae", "body : "+body);

            return body;

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Log.i("yunjae", aVoid);

            Gson gson = new Gson();

            List<ReviewInfo> info = gson.fromJson(aVoid, new TypeToken<List<FoodTruckInfo>>(){}.getType());

            //foodTruckInfos = info; //푸드트럭 인포 받아옴

            for(int i=0; i<foodTruckInfos.size();i++) {

                Log.i("haneul_list_test", "info" + foodTruckInfos.get(i).getStoreName() + "////" + foodTruckInfos.size());
            }

            tabLayout = (TabLayout)findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("주문하기"));
            tabLayout.addTab(tabLayout.newTab().setText("리뷰작성"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
            tabLayout.setTabTextColors(R.color.white,getResources().getColor(R.color.black));


            viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
            viewPager.setOffscreenPageLimit(2);


            currentMenu = R.id.navigation_home;
            setupViewPager(viewPager);

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





        /*    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            currentMenu = R.id.navigation_home;
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.navigation_map:
                            currentMenu = R.id.navigation_map;
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.navigation_list:
                            currentMenu = R.id.navigation_list;
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.navigation_order:
                            currentMenu = R.id.navigation_order;
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    return true;
                }
            });
*/



        }
    }
}
