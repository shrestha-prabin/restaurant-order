package com.example.prabin.restaurant.navigation_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.adapter.TransactionTableRVAdapter;
import com.example.prabin.restaurant.helper.DatabaseHelper;
import com.example.prabin.restaurant.modal.OrderItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionTableFragment extends Fragment {

    private List<OrderItem> orderItemList = new ArrayList<>();
    private RecyclerView rvTransactionTable;
    private TransactionTableRVAdapter mAdapter;

    public TransactionTableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_table, container, false);

        rvTransactionTable = view.findViewById(R.id.rvTransactionTable);
        orderItemList = new DatabaseHelper(getContext()).getOrders();

        mAdapter = new TransactionTableRVAdapter(orderItemList,getContext());
        rvTransactionTable.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransactionTable.setItemAnimator(new DefaultItemAnimator());
        rvTransactionTable.setAdapter(mAdapter);
        rvTransactionTable.addItemDecoration(new DividerItemDecoration(rvTransactionTable.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }

    private void fetchData() {
        orderItemList = new DatabaseHelper(getContext()).getOrders();
        mAdapter.updateDataList(orderItemList);
    }


}
