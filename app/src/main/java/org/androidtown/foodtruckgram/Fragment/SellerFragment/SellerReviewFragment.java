package org.androidtown.foodtruckgram.Fragment.SellerFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.foodtruckgram.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerReviewFragment extends Fragment {


    public SellerReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_review, container, false);


        return view;
    }

}