package org.androidtown.foodtruckgram.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.foodtruckgram.Info.MenuInfo;
import org.androidtown.foodtruckgram.R;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2018-09-11.
 */

public class SellerMenuListAdapter extends RecyclerView.Adapter<SellerMenuListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView menuImage;
        public TextView name,price,introduce;
        public ImageButton remove,edit;
        private SellerMenuListAdapter adapter;
        private Context context;

        public ViewHolder(final Context context, View itemView, final SellerMenuListAdapter adapter) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.context = context;
            this.adapter = adapter;

            menuImage = (ImageView)itemView.findViewById(R.id.menuImage_imageView);
            name = (TextView)itemView.findViewById(R.id.menuName_textView);
            price = (TextView)itemView.findViewById(R.id.menuPrice_textView);
            introduce = (TextView)itemView.findViewById(R.id.menuIntroduce_textView);

            remove = (ImageButton)itemView.findViewById(R.id.menuItemRemoveBtn);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    Toast.makeText(context, name.getText() + Integer.toString(position), Toast.LENGTH_SHORT).show();
                    adapter.removeItem(position);
                }
            });

            edit = (ImageButton)itemView.findViewById(R.id.menuItemEditBtn);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    Toast.makeText(context, name.getText() + Integer.toString(position) + "Edit", Toast.LENGTH_SHORT).show();
                }
            });



        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            // We can access the data within the views
            Toast.makeText(context, name.getText() + Integer.toString(position), Toast.LENGTH_SHORT).show();
            adapter.removeItem(position);

        }
    }

    // Store a member variable for the contacts
    private ArrayList<MenuInfo> menuList;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public SellerMenuListAdapter(Context context, ArrayList<MenuInfo> menuList) {
        this.menuList = menuList;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SellerMenuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_seller_menu, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context, contactView, this);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SellerMenuListAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        MenuInfo menuInfo = menuList.get(position);

        // Set item views based on your views and data model
        viewHolder.menuImage.setImageResource(R.drawable.menu);  //menu image edit
        viewHolder.name.setText(menuInfo.getMenuName());
        viewHolder.price.setText(menuInfo.getMenuPrice());
        viewHolder.introduce.setText(menuInfo.getMenuIntroduce());

        Log.i("RecyclerView","name : "+ menuInfo.getMenuName() + " / price : "+menuInfo.getMenuPrice());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void removeItem(int p) {
        menuList.remove(p);
        notifyItemRemoved(p);

    }
}
