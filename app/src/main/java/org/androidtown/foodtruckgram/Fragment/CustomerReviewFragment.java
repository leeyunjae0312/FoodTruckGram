package org.androidtown.foodtruckgram.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidtown.foodtruckgram.Adapter.CustomerReviewAdapter;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerReviewFragment extends Fragment {

    private View view;
    private ListView reviewListView = null;
    private FoodTruckInfo foodTruckInfo;

    public CustomerReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_customer_review, container, false);



        Bundle bundle = getArguments();

        foodTruckInfo = (FoodTruckInfo) bundle.getSerializable("foodturckInfo");

        UserInfo userInfo = UserInfo.getUserInfo();

        ArrayList<FoodTruckInfo> foodTruckInfos = new ArrayList<>();

        for (int i=0; i<10; ++i)
        {
            FoodTruckInfo foodTruckInfo = new FoodTruckInfo();
            foodTruckInfo.setStoreName("이름"+i);
            foodTruckInfo.setOwnerId("오너"+i);
            foodTruckInfos.add(i,foodTruckInfo);
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        reviewListView = (ListView)view.findViewById(R.id.review_listview);
        CustomerReviewAdapter adapter = new CustomerReviewAdapter(foodTruckInfos);
        reviewListView.setAdapter(adapter);

        return view;
    }

}
