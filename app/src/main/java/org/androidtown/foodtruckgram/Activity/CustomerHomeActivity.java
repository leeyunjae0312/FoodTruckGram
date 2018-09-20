package org.androidtown.foodtruckgram.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidtown.foodtruckgram.Adapter.ViewPagerAdapter;
import org.androidtown.foodtruckgram.Fragment.HomeFragment;
import org.androidtown.foodtruckgram.Fragment.MapFragment;
import org.androidtown.foodtruckgram.Fragment.MyOrderFragment;
import org.androidtown.foodtruckgram.Fragment.TruckListFragment;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerHomeActivity extends AppCompatActivity {

    ActionBar actionBar;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    //FavoriteFragment favoriteFragment;
    MapFragment mapFramgment;
    MyOrderFragment myOrderFragment;
    TruckListFragment truckListFragment;

    int currentMenu;
    MenuItem prevMenuItem;

    UserInfo userInfo = UserInfo.getUserInfo();
    List<FoodTruckInfo> foodTruckInfos;
    String serverURL_getFoodTruckInfoList = "http://" + HttpClient.ipAdress + HttpClient.serverPort + HttpClient.urlBase + "/c/getFoodTruckInfoList";
    String serverURL_getFavoriteTruck = "http://" + HttpClient.ipAdress + HttpClient.serverPort + HttpClient.urlBase + "/c/getFavoriteStoreByUserId";
    FoodTruckDB foodTruckDB;
    FavoriteTruckDB favoriteTruckDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        foodTruckDB = new FoodTruckDB();
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userInfo.getUserId());
        foodTruckDB.execute(params);

        favoriteTruckDB = new FavoriteTruckDB();
        favoriteTruckDB.execute(params);

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 4);

        homeFragment = new HomeFragment();
        mapFramgment = new MapFragment();
        truckListFragment = new TruckListFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("foodTruckInfos",(Serializable) foodTruckInfos);
        truckListFragment.setArguments(bundle);

        myOrderFragment = new MyOrderFragment();
        myOrderFragment.setArguments(bundle);

        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(mapFramgment);
        viewPagerAdapter.addFragment(truckListFragment);
        viewPagerAdapter.addFragment(myOrderFragment);

        viewPager.setAdapter(viewPagerAdapter);
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

            List<FoodTruckInfo> info = gson.fromJson(aVoid, new TypeToken<List<FoodTruckInfo>>(){}.getType());

            foodTruckInfos = info;

            viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setOffscreenPageLimit(4);

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

            currentMenu = R.id.navigation_home;
            setupViewPager(viewPager);
            prevMenuItem = bottomNavigationView.getMenu().getItem(0);


            actionBar = getSupportActionBar();


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

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
                            ////foodTruckInfos = info;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("foodtruckINFO", (Serializable) foodTruckInfos);
                            truckListFragment.setArguments(bundle);
                            break;
                        case R.id.navigation_order:
                            currentMenu = R.id.navigation_order;
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
    }

    class FavoriteTruckDB extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>...maps) {

            HttpClient.Builder http = new HttpClient.Builder("POST", serverURL_getFavoriteTruck);
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

            ArrayList<FoodTruckInfo> info = gson.fromJson(aVoid, new TypeToken<List<FoodTruckInfo>>(){}.getType());
            UserInfo.getUserInfo().setMyFavoriteList(info);
        }
    }


}
