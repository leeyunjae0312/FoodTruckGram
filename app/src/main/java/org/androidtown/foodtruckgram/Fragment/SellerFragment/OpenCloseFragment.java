package org.androidtown.foodtruckgram.Fragment.SellerFragment;


import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenCloseFragment extends Fragment {

    View view;

    RelativeLayout mapLayout;
    TMapView tMapView;
    String apiKey = "1c8d9930-5a2b-4ed2-8c32-fa7d9a216834";
    double longitude,latitude;
    FoodTruckInfo foodTruckInfo;

    public OpenCloseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_open_close, container, false);

        foodTruckInfo = (FoodTruckInfo)getArguments().getSerializable("foodTruckInfo");

        mapLayout = (RelativeLayout) view.findViewById(R.id.sellerTmapFragment);
        tMapView = new TMapView(getActivity());

        //세팅
        tMapView.setSKTMapApiKey(apiKey);
        tMapView.setLocationPoint(126.970325,37.556152);
        tMapView.setCenterPoint(126.970325,37.556152);
        tMapView.setCompassMode(false);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setTrackingMode(false);
        tMapView.setSightVisible(false);
        mapLayout.addView(tMapView);

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Toast.makeText(getActivity(), "onPress~!", Toast.LENGTH_SHORT).show();
                tMapView.setCenterPoint(tMapPoint.getLongitude(),tMapPoint.getLatitude());
                latitude = tMapPoint.getLatitude();
                longitude = tMapPoint.getLongitude();
                Log.i("Tmap","longitude = "+longitude+"latitude = "+latitude);
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Toast.makeText(getActivity(), "onPressUp~!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final TextView textView = (TextView)view.findViewById(R.id.seller_openPage_textView);
        final TextView openBtn = (TextView)view.findViewById(R.id.openBtn);
        final TextView closeBtn = (TextView)view.findViewById(R.id.closeBtn);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("현재 위치에서 폐점하시겠습니까?");
                openBtn.setVisibility(View.INVISIBLE);
                closeBtn.setVisibility(View.VISIBLE);

                foodTruckInfo.opening(longitude,latitude);

                Log.i("OpenClose","longitude = "+longitude+"latitude = "+latitude);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("현재 위치에서 개점하시겠습니까?");
                closeBtn.setVisibility(View.INVISIBLE);
                openBtn.setVisibility(View.VISIBLE);

                foodTruckInfo.closing();
            }
        });

        return view;
    }



}
