package com.example.prabin.restaurant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.modal.MenuItem;
import com.example.prabin.restaurant.modal.OrderItem;

import java.util.ArrayList;

public class MenuItemListRVAdapter extends RecyclerView.Adapter<MenuItemListRVAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuItem> mMenuItemList;
    private PrefManager mPrefManager;

    public MenuItemListRVAdapter(Context context, ArrayList<MenuItem> menuItemList) {
        this.mContext = context;
        this.mMenuItemList = menuItemList;

        mPrefManager = new PrefManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final MenuItem item = mMenuItemList.get(i);

        Glide.with(mContext).load(item.getImage()).centerCrop().into(viewHolder.ivImage);
        viewHolder.tvItemName.setText(item.getName());
        viewHolder.tvItemDescription.setText(item.getDescription());
        viewHolder.tvPrice.setText("Rs. " + item.getPrice());
        viewHolder.tvCount.setText(String.valueOf(item.getOrderCount()));

        viewHolder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetItemCount(i);

                decreaseOrderCount(i);
                mPrefManager.setOrderItemName(item.getName());
                mPrefManager.setOrderItemCount(item.getOrderCount());
                mPrefManager.setOrderItemRate(item.getPrice());
            }
        });

        viewHolder.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetItemCount(i);

                increaseOrderCount(i);
                mPrefManager.setOrderItemName(item.getName());
                mPrefManager.setOrderItemCount(item.getOrderCount());
                mPrefManager.setOrderItemCount(item.getOrderCount());
                mPrefManager.setOrderItemRate(item.getPrice());
            }
        });
    }

    private void resetItemCount(int i) {
        for (int j = 0; j < this.getItemCount(); j++) {
            // Reset count of other items other than the selected
            if (j != i)
                mMenuItemList.get(j).setOrderCount(0);
        }
        this.notifyDataSetChanged();
    }

    private void increaseOrderCount(int i) {
        MenuItem item = mMenuItemList.get(i);

        int count = item.getOrderCount();
        if (count < 9) {
            count++;
            item.setOrderCount(count);
        }

        mMenuItemList.set(i, item);
        this.notifyDataSetChanged();
    }

    private void decreaseOrderCount(int i) {
        MenuItem item = mMenuItemList.get(i);

        int count = item.getOrderCount();
        if (count > 0) {
            count--;
            item.setOrderCount(count);
        }

        mMenuItemList.set(i, item);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvItemName, tvCount, tvItemDescription, tvPrice;
        ImageButton ibAdd, ibRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.menu_item_image);
            tvItemName = itemView.findViewById(R.id.menu_item_name);
            tvItemDescription = itemView.findViewById(R.id.menu_item_description);
            tvPrice = itemView.findViewById(R.id.menu_item_price);
            tvCount = itemView.findViewById(R.id.menu_item_count);
            ibAdd = itemView.findViewById(R.id.menu_item_increase);
            ibRemove = itemView.findViewById(R.id.menu_item_decrease);
        }
    }

    public void resetOrder() {
        for (int j = 0; j < this.getItemCount(); j++) {
                mMenuItemList.get(j).setOrderCount(0);
        }
        this.notifyDataSetChanged();
    }
}
