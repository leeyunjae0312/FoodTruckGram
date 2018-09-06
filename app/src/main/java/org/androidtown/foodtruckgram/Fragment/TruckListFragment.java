package org.androidtown.foodtruckgram.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.foodtruckgram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TruckListFragment extends Fragment {


    public TruckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truck_list, container, false);
    }

}
