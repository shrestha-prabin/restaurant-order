package com.example.prabin.restaurant.helper;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.prabin.restaurant.modal.OrderItem;

import java.util.List;

public class TransactionDiffCallback extends DiffUtil.Callback {

    private List<OrderItem> oldTableItems;
    private List<OrderItem> newTableItems;

    public TransactionDiffCallback(List<OrderItem> oldTableItems, List<OrderItem> newTableItems) {
        this.oldTableItems = oldTableItems;
        this.newTableItems = newTableItems;
    }

    @Override
    public int getOldListSize() {
        return oldTableItems.size();
    }

    @Override
    public int getNewListSize() {
        return newTableItems.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldTableItems.get(i).getKey().equals(newTableItems.get(i1).getKey());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldTableItems.get(i).equals(newTableItems.get(i1));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
