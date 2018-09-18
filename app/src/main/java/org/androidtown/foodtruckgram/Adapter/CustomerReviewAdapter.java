package org.androidtown.foodtruckgram.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Haneul on 2018-09-18.
 */

public class CustomerReviewAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<FoodTruckInfo> foodTruckInfos = null;
    private int listCount = 0;



    private UserInfo userInfo = UserInfo.getUserInfo();

    public CustomerReviewAdapter(ArrayList<FoodTruckInfo> foodTruckInfos) {
        this.foodTruckInfos = foodTruckInfos;
        listCount = foodTruckInfos.size();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {

            Context context = parent.getContext();

            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.customer_review_item, parent, false);
        }

        ImageView userImg = (ImageView) convertView.findViewById(R.id.user_id);
        TextView userID = (TextView) convertView.findViewById(R.id.user_id);
        TextView reviewContent = (TextView) convertView.findViewById(R.id.review_content);
        TextView reviewDate = (TextView) convertView.findViewById(R.id.review);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String getDate = sdf.format(date);

        Log.i("haneul","test"+date);

      //  userImg.setImageResource(R.drawable.foodtruckgram); //프로필 사진
        userID.setText("userID");
      //  reviewContent.setText();//리뷰 디비에서
       reviewDate.setText(getDate);
       reviewContent.setText(foodTruckInfos.get(position).getStoreName());

     /*   /////////////////////다시해야함!
        final ImageButton foodtruckFavoriteBtn = (ImageButton) convertView.findViewById(R.id.favorite_btn);

        final View finalConvertView = convertView;
        foodtruckFavoriteBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < listCount; i++) {
                    if (userInfo.getMyFavoriteList() != null && userInfo.getMyFavoriteList().get(i).getStoreName().equals(foodtruckName.getText())) {
                        foodtruckFavoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(finalConvertView.getContext(), "즐겨찾기에서 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        userInfo.getMyFavoriteList().add(foodTruckInfos.get(position));
                        foodtruckFavoriteBtn.setImageResource(R.drawable.list_red_favorite_btn);
                        Toast.makeText(finalConvertView.getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
*/
        return convertView;
    }
}
