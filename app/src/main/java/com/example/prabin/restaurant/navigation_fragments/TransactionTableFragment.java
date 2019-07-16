package com.example.prabin.restaurant.navigation_fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<String> orderItemKeysList = new ArrayList<>();

    private RecyclerView rvTransactionTable;
    private TransactionTableRVAdapter mAdapter;
    private DatabaseHelper dbHelper;
    FloatingActionButton fabAddOrder;

    public TransactionTableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_table, container, false);

        fabAddOrder = getActivity().findViewById(R.id.fab_addOrder);
        rvTransactionTable = view.findViewById(R.id.rvTransactionTable);
        dbHelper = new DatabaseHelper(getContext());
//        orderItemList = dbHelper.getOrders();

        mAdapter = new TransactionTableRVAdapter(orderItemList, orderItemKeysList, getContext());

        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
//        llManager.setReverseLayout(true);
        rvTransactionTable.setLayoutManager(llManager);
        rvTransactionTable.setItemAnimator(new DefaultItemAnimator());
        rvTransactionTable.setAdapter(mAdapter);
        rvTransactionTable.addItemDecoration(new DividerItemDecoration(rvTransactionTable.getContext(), DividerItemDecoration.VERTICAL));

        hideFABOnScroll();
        fetchData();
        listenDataChanges();
        return view;
    }

    private void hideFABOnScroll() {
        // hide fab on scroll, show after 1s
        rvTransactionTable.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0) fabAddOrder.hide();
//                else if (dy < 0) fabAddOrder.show();
                fabAddOrder.hide();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fabAddOrder.show();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void fetchData() {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");

        orderRef.orderByChild("completed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderItemList.clear();
                orderItemKeysList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        orderItemList.add(dsp.getValue(OrderItem.class));
                        orderItemKeysList.add(dsp.getKey());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listenDataChanges() {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");
        orderRef.keepSynced(true);

        orderRef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData();
                rvTransactionTable.scrollToPosition(0);

//                OrderItem item = dataSnapshot.getValue(OrderItem.class);
//
//                String key = dataSnapshot.getKey();
//                if (!orderItemKeysList.contains(key)) {
//                    orderItemKeysList.add(dataSnapshot.getKey());
//                    orderItemList.add(item);
//                    mAdapter.notifyDataSetChanged();
//                }

//                dbHelper.addNewOrder(item);
//                updateDisplayTable();


//                orderItemList = dbHelper.getOrders();

//                mAdapter.updateDataList(orderItemList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData();
//                OrderItem updatedItem = dataSnapshot.getValue(OrderItem.class);
//                String key = dataSnapshot.getKey();

//                mAdapter.updateDataItem(key, updatedItem);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                fetchData();
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

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

//    private void updateDisplayTable() {
//
//        orderItemList = dbHelper.getOrders();
//
//        mAdapter.updateDataList(orderItemList);
//    }

}
