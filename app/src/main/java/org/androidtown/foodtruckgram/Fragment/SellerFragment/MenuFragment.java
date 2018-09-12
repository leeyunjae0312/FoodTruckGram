package org.androidtown.foodtruckgram.Fragment.SellerFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.foodtruckgram.Activity.SellerHomeActivity;
import org.androidtown.foodtruckgram.Adapter.SellerMenuListAdapter;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.MenuInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MenuInfo> menuList;
    private SellerMenuListAdapter adapter;

    public MenuFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.seller_menu_recyclerView);

        FoodTruckInfo foodTruckInfo = (FoodTruckInfo)getArguments().getSerializable("foodTruckInfo");
        menuList = foodTruckInfo.getMenuList();

        /*menuList.add(new MenuInfo("새우버터구이","6000원","menuImage","새우+버터+파슬리"));
        menuList.add(new MenuInfo("칠리새우","8000","menuImage"));
        menuList.add(new MenuInfo("큐브스테이크","10000","menuImage"));*/

        recyclerView.setHasFixedSize(true);
        adapter = new SellerMenuListAdapter(getActivity(),menuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

}
