package com.example.prabin.restaurant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.helper.TransactionDiffCallback;
import com.example.prabin.restaurant.modal.OrderItem;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransactionTableRVAdapter extends RecyclerView.Adapter<TransactionTableRVAdapter.ViewHolder> {

    private int colorMaroon, colorOrange, colorGreen, colorWhite, colorGray;
    private List<OrderItem> orderItemList;

    private Context mContext;

    DatabaseReference mOrderRef = FirebaseDatabase.getInstance().getReference("orders");

    public TransactionTableRVAdapter(List<OrderItem> orderItemList, Context context) {

        this.orderItemList = orderItemList;
        this.mContext = context;

        this.colorMaroon = context.getResources().getColor(R.color.maroon);
        this.colorOrange = context.getResources().getColor(R.color.orange);
        this.colorGreen = context.getResources().getColor(R.color.green);
        this.colorWhite = mContext.getResources().getColor(R.color.white);
        this.colorGray = mContext.getResources().getColor(R.color.gray);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSN, tvTime, tvTableNo, tvItems, tvQuantity, tvPacking, tvRemarks, tvKitchenProcess, tvChef;
        FlexboxLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSN = itemView.findViewById(R.id.tablerow_tvSN);
            tvTime = itemView.findViewById(R.id.tablerow_tvTime);
            tvTableNo = itemView.findViewById(R.id.tablerow_tvTableNo);
            tvItems = itemView.findViewById(R.id.tablerow_tvItems);
            tvQuantity = itemView.findViewById(R.id.tablerow_tvQuantity);
            tvPacking = itemView.findViewById(R.id.tablerow_tvPacking);
            tvRemarks = itemView.findViewById(R.id.tablerow_tvRemarks);
            tvKitchenProcess = itemView.findViewById(R.id.tablerow_tvKitchenProcess);
            tvChef = itemView.findViewById(R.id.tablerow_tvChef);

            layout = itemView.findViewById(R.id.tablerow);
        }
    }

    @NonNull
    @Override
    public TransactionTableRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.transaction_table_row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionTableRVAdapter.ViewHolder viewHolder, final int i) {

        OrderItem orderItem = orderItemList.get(i);

        viewHolder.tvSN.setText(String.valueOf(i + 1));
        viewHolder.tvTime.setText(orderItem.getTime());
        viewHolder.tvTableNo.setText(orderItem.getTableNumber());
        viewHolder.tvItems.setText(orderItem.getItems());
        viewHolder.tvQuantity.setText(orderItem.getQuantity());
        viewHolder.tvPacking.setText(orderItem.getPacking());
        viewHolder.tvRemarks.setText(orderItem.getRemarks());
        viewHolder.tvKitchenProcess.setText(orderItem.getKitchenProcess());
        viewHolder.tvChef.setText(orderItem.getChefName());

        viewHolder.tvSN.setTextColor(colorWhite);
        viewHolder.tvTime.setTextColor(colorWhite);
        viewHolder.tvTableNo.setTextColor(colorWhite);
        viewHolder.tvItems.setTextColor(colorWhite);
        viewHolder.tvQuantity.setTextColor(colorWhite);
        viewHolder.tvPacking.setTextColor(colorWhite);
        viewHolder.tvRemarks.setTextColor(colorWhite);
        viewHolder.tvKitchenProcess.setTextColor(colorWhite);
        viewHolder.tvChef.setTextColor(colorWhite);

        switch (orderItem.getKitchenProcess().toLowerCase()) {
            case "order":
                viewHolder.layout.setBackgroundColor(colorMaroon);
                break;
            case "cooking":
                viewHolder.layout.setBackgroundColor(colorOrange);
                break;
            case "cooked":
                viewHolder.layout.setBackgroundColor(colorGreen);
                break;
            default:
                viewHolder.tvSN.setTextColor(colorGray);
                viewHolder.tvTime.setTextColor(colorGray);
                viewHolder.tvTableNo.setTextColor(colorGray);
                viewHolder.tvItems.setTextColor(colorGray);
                viewHolder.tvQuantity.setTextColor(colorGray);
                viewHolder.tvPacking.setTextColor(colorGray);
                viewHolder.tvRemarks.setTextColor(colorGray);
                viewHolder.tvKitchenProcess.setTextColor(colorGray);
                viewHolder.tvChef.setTextColor(colorGray);
                viewHolder.layout.setBackgroundColor(colorWhite);
                break;
        }

        final String kitchenProcess = orderItem.getKitchenProcess().toLowerCase();

        viewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                OrderItem item = orderItemList.get(i);
                switch (kitchenProcess) {
                    case "order":
                        setChefName(i);
                        updateKitchenProcess(i, "Cooking");
//                        item.setKitchenProcess("Cooking");
                        break;
                    case "cooking":
                        updateKitchenProcess(i, "Cooked");
//                        item.setKitchenProcess("Cooked");
                        break;
                    case "cooked":
                        updateKitchenProcess(i, "Complete");
                        setOrderCompletion(i);
//                        item.setKitchenProcess("Complete");
//                        item.setCompleted("1");
                        break;
                }
//                updateDataItem(i, item);
                return false;
            }
        });
    }

    private void updateKitchenProcess(int index, String process) {
        OrderItem item = orderItemList.get(index);
        item.setKitchenProcess(process);

        mOrderRef.child(item.getKey()).setValue(item);
    }

    private void setOrderCompletion(int index) {
        OrderItem item = orderItemList.get(index);
        item.setCompleted("1");
        mOrderRef.child(item.getKey()).setValue(item);
    }

    private void setChefName(int index) {
        OrderItem item = orderItemList.get(index);

        item.setChefName(new PrefManager(mContext).getUserDetails().getFullName());
        mOrderRef.child(item.getKey()).setValue(item);
    }

    public void updateDataList(List<OrderItem> newList) {
        if (this.orderItemList.isEmpty()) {
            orderItemList.clear();
            this.orderItemList.addAll(newList);
            this.notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TransactionDiffCallback(this.orderItemList, newList));
            diffResult.dispatchUpdatesTo(this);
        }
    }

//    public void updateDataList(List<OrderItem> data) {
//        orderItemList.clear();
//        orderItemList.addAll(data);
//        this.notifyDataSetChanged();
//    }

//    public void updateDataItem(String key, OrderItem updatedItem) {
//
//        int index = orderItemKeysList.indexOf(key);
//        if(updatedItem.getCompleted().equals("1")) {
//            orderItemList.remove(index);
////            orderItemList.add(updatedItem);
////            this.updateDataList(orderItemList);
//        } else {
//        this.updateDataItem(index, updatedItem);
//        }
//    }

//    private void updateDataItem(int index, OrderItem item) {
//        orderItemList.set(index, item);
//        this.notifyItemChanged(index);
//    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }
}
