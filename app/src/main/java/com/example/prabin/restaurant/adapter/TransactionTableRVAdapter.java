package com.example.prabin.restaurant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.modal.OrderItem;

import java.util.List;

public class TransactionTableRVAdapter extends RecyclerView.Adapter<TransactionTableRVAdapter.ViewHolder> {

    private List<OrderItem> orderItemList;
    private Context mContext;

    public TransactionTableRVAdapter(List<OrderItem> orderItemList, Context context) {
        this.orderItemList = orderItemList;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSN, tvTime, tvTableNo, tvItems, tvQuantity, tvPacking, tvRemarks, tvKitchenProcess, tvChef;

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
        }
    }

    @NonNull
    @Override
    public TransactionTableRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.transaction_table_row, viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionTableRVAdapter.ViewHolder viewHolder, int i) {
        OrderItem orderItem = orderItemList.get(i);
        viewHolder.tvSN.setText(String.valueOf(i+1));
        viewHolder.tvTime.setText(orderItem.getTime());
        viewHolder.tvTableNo.setText(orderItem.getTableNumber());
        viewHolder.tvItems.setText(orderItem.getItems());
        viewHolder.tvQuantity.setText(orderItem.getQuantity());
        viewHolder.tvPacking.setText(orderItem.getPacking());
        viewHolder.tvRemarks.setText(orderItem.getRemarks());
        viewHolder.tvKitchenProcess.setText(orderItem.getKitchenProcess());
        viewHolder.tvChef.setText(orderItem.getChefName());
    }

    public void updateDataList(List<OrderItem> data) {
        orderItemList.clear();
        orderItemList.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }
}
