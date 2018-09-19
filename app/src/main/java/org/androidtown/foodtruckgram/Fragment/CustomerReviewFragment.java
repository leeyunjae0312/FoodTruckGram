package org.androidtown.foodtruckgram.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerReviewFragment extends Fragment {

    private View view;
    private ListView reviewListView = null;
    ArrayList<FoodTruckInfo> foodTruckInfos;


    public CustomerReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_customer_review, container, false);

        foodTruckInfos = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            FoodTruckInfo foodTruckInfo = new FoodTruckInfo();
            foodTruckInfo.setOwnerId("푸드트럭 오너" + i);
            foodTruckInfo.setStoreName("푸드트럭 이름" + i);
            foodTruckInfos.add(foodTruckInfo);


        }

        ListView listview ;
        CustomerReviewAdapter adapter;

        // Adapter 생성
        adapter = new CustomerReviewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.review_listview);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_account_box_black_36dp),
                "Box", "Account Box Black 36dp") ;




       /* // ListView, Adapter 생성 및 연결 ------------------------
        reviewListView = (ListView) view.findViewById(R.id.review_listview);
        CustomerReviewAdapter customerReviewAdapter = new CustomerReviewAdapter(foodTruckInfos);
        reviewListView.setAdapter(customerReviewAdapter);*/

        return view;
    }

}
