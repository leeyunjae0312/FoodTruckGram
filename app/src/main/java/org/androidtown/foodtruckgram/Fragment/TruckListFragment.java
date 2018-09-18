package org.androidtown.foodtruckgram.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.androidtown.foodtruckgram.Adapter.FoodTruckListAdapter;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TruckListFragment extends Fragment {

    private View view;



    private ArrayList<FoodTruckInfo> foodTruckInfos = new ArrayList<FoodTruckInfo>();
    private ListView foodTruckListView = null;

    public TruckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_truck_list, container, false);

        Bundle bundle = getArguments();
//        foodTruckInfos = (ArrayList)bundle.getSerializable("foodtruckINFO");


        for (int i=0; i<10; ++i)
        {
            FoodTruckInfo foodTruckInfo = new FoodTruckInfo();
            foodTruckInfo.setOwnerId("푸드트럭 오너" + i);
            foodTruckInfo.setStoreName("푸드트럭 이름" + i);
            foodTruckInfos.add(foodTruckInfo);
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        foodTruckListView = (ListView)view.findViewById(R.id.foodtruck_list_view);
        FoodTruckListAdapter foodTruckListAdapter = new FoodTruckListAdapter(foodTruckInfos);
        foodTruckListView.setAdapter(foodTruckListAdapter);

        return view;
    }

}
