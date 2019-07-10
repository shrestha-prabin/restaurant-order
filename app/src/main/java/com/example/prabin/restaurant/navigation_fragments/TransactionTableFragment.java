package com.example.prabin.restaurant.navigation_fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.adapter.TransactionTableRVAdapter;
import com.example.prabin.restaurant.helper.DatabaseHelper;
import com.example.prabin.restaurant.modal.OrderItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionTableFragment extends Fragment {

    private List<OrderItem> orderItemList = new ArrayList<>();
    private RecyclerView rvTransactionTable;
    private TransactionTableRVAdapter mAdapter;
    private DatabaseHelper dbHelper;

    public TransactionTableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_table, container, false);

        rvTransactionTable = view.findViewById(R.id.rvTransactionTable);
//        orderItemList = new DatabaseHelper(getContext()).getOrders();

        dbHelper = new DatabaseHelper(getContext());

        mAdapter = new TransactionTableRVAdapter(orderItemList, view.getContext());
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
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");

        orderRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                OrderItem item = dataSnapshot.getValue(OrderItem.class);

                Log.d("fire_order", item.getItems());
//                dbHelper.addNewOrder(item);
//                updateDisplayTable();


                orderItemList.add(item);
//                orderItemList = dbHelper.getOrders();

//                mAdapter.notifyDataSetChanged();
                mAdapter.updateDataList(orderItemList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        orderItemList = new DatabaseHelper(getContext()).getOrders();
//        mAdapter.updateDataList(orderItemList);
    }

    private void updateDisplayTable() {

        orderItemList = dbHelper.getOrders();

        mAdapter.updateDataList(orderItemList);
    }


}
