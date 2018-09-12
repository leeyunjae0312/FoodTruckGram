package org.androidtown.foodtruckgram.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.androidtown.foodtruckgram.Info.MapPoint;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private View view;

    private RelativeLayout mapLayout;

    private Handler handler = null;
    private String searchData; //////검색 주소

    private Button searchBtn;
    private AutoCompleteTextView searchBar;

    //flaoting Button
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;


    private TMapGpsManager gps;
    private TMapView tMapView;
    private TMapData tmapdata;

    private static int mMarkerID;
    private static String apiKey = "1c8d9930-5a2b-4ed2-8c32-fa7d9a216834";

  //  private ArrayList<TMapPoint> arrayTmapPoint = new ArrayList<>();
    private ArrayList<String> arrayAddressName = new ArrayList<>();
    private ArrayList<MapPoint> arrayMapPoint = new ArrayList<>();

    SearchView searchView;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);


        //floating button
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab_current_location); //현재위치
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                anim();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setCurrentLocation();
            }
        });


        tMapView = new TMapView(getActivity());

        //세팅
        tMapView.setSKTMapApiKey(apiKey);
        tMapView.setCompassMode(true);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);  //일반지도
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        gps = new TMapGpsManager(getActivity());

        //map view
        mapLayout = (RelativeLayout) view.findViewById(R.id.tmapFragment);
        mapLayout.addView(tMapView);


        //권한
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); //위치권한 탐색 허용 관련 내용
            }
        }

        //주소 검색하기
        //searchBtn = (Button)view.findViewById(R.id.mapSearchBtn);
        searchBar = (AutoCompleteTextView) view.findViewById(R.id.mapSearchBar);

        searchBar.setBackgroundColor(Color.WHITE);
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchBar.setInputType(InputType.TYPE_CLASS_TEXT);
        searchBar.setHint("위치 검색");

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (EditorInfo.IME_ACTION_SEARCH == 1)
                    Toast.makeText(getActivity().getApplicationContext(), "검색", Toast.LENGTH_LONG).show();
                    searchData = searchBar.getText().toString();
                    searchAddress(searchData);
                   return true;
            }
        });

        searchBar.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayAddressName));



/*
///위치정보 주소로 변환 코드
        try {
            final String address = new TMapData().convertGpsToAddress(tMapView.getLatitude(), tMapView.getLongitude());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext() , address, Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e) {
            e.printStackTrace();
        }*/

////터치 테스트////
/*        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
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
        });*/

        return view;
    }

    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    //last 현재 위치
    public void setCurrentLocation() {
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        double latitude = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
        double longitude = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();

        tMapView.setLocationPoint(longitude, latitude);
        tMapView.setCenterPoint(longitude, latitude);

    }

    //주소검색
    public void searchAddress(String strData) {
        tmapdata = new TMapData();
        tmapdata.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {

                TMapPOIItem searchItem = (TMapPOIItem) poiItem.get(0);
                double longitude = searchItem.getPOIPoint().getLongitude();
                double latitude = searchItem.getPOIPoint().getLatitude();
                tMapView.setLocationPoint(longitude, latitude);
                tMapView.setCenterPoint(longitude, latitude);

                for (int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                    arrayMapPoint.add(new MapPoint(item.getPOIName().toString(),item.getPOIAddress().toString(),item.getPOIPoint()));
                    arrayAddressName.add(item.getPOIName());

                    Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                            "Address: " + item.getPOIAddress().replace("null", "") + ", " +
                            "Point: " + item.getPOIPoint().toString());
                }



            }
        });
    }
}
