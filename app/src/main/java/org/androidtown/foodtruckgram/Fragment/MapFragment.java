package org.androidtown.foodtruckgram.Fragment;


import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    View view;

    RelativeLayout mapLayout;
    TMapView tMapView;
    String apiKey = "1c8d9930-5a2b-4ed2-8c32-fa7d9a216834";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);

        mapLayout = (RelativeLayout) view.findViewById(R.id.tmapFragment);
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

////터치 테스트////
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Toast.makeText(getActivity(), "onPress~!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                //Toast.makeText(MapEvent.this, "onPressUp~!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;
    }

}
