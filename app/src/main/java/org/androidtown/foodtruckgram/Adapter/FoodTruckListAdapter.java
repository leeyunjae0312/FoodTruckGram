package org.androidtown.foodtruckgram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.foodtruckgram.Activity.CustomerHomeActivity;
import org.androidtown.foodtruckgram.Activity.DetailActivity;
import org.androidtown.foodtruckgram.Activity.LoginActivity;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * Created by Haneul on 2018-09-13.
 */

public class FoodTruckListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<FoodTruckInfo> foodTruckInfos = null;
    private int listCount = 0;
    private int count=0;

    private UserInfo userInfo = UserInfo.getUserInfo();


    public FoodTruckListAdapter(ArrayList<FoodTruckInfo> foodTruckInfos)
    {
        this.foodTruckInfos = foodTruckInfos;
        listCount = foodTruckInfos.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return listCount;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.foodtruck_list_item, parent, false);
        }

        ImageView foodtruckProfile = (ImageView) convertView.findViewById(R.id.foodtruck_profile);
        final TextView foodtruckName = (TextView) convertView.findViewById(R.id.foodtruck_name);
        TextView foodtruckID = (TextView) convertView.findViewById(R.id.foodtruck_id);
        ImageView foodtruckImg = (ImageView) convertView.findViewById(R.id.foodtruck_img);
        TextView foodtruckComment = (TextView) convertView.findViewById(R.id.foodtruck_comment);

        foodtruckProfile.setImageResource(R.drawable.foodtruckgram); //프로필 사진
        foodtruckName.setText(foodTruckInfos.get(position).getStoreName());
        foodtruckID.setText(foodTruckInfos.get(position).getOwnerId());
        foodtruckImg.setImageResource(R.drawable.sample); //음식사진
        foodtruckComment.setText("코멘트 추가해야 함");



        /////////////////////다시해야함!
        final ImageButton foodtruckFavoriteBtn = (ImageButton) convertView.findViewById(R.id.favorite_btn);

        final View finalConvertView = convertView;
        foodtruckFavoriteBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override public void onClick(View view) {

                for (int i = 0; i < listCount; i++) {
                    if (userInfo.getMyFavoriteList() != null && userInfo.getMyFavoriteList().get(i).getStoreName().equals(foodtruckName.getText())) {
                        foodtruckFavoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(finalConvertView.getContext(), "즐겨찾기에서 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        userInfo.getMyFavoriteList().add(foodTruckInfos.get(position));
                        foodtruckFavoriteBtn.setImageResource(R.drawable.list_red_favorite_btn);
                        Toast.makeText(finalConvertView.getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }
            }
        });

        ImageButton detailBtn = (ImageButton) convertView.findViewById(R.id.detail_page_btn);


        detailBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(finalConvertView.getContext(), DetailActivity.class);
                finalConvertView.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}