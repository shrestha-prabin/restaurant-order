package com.example.prabin.restaurant.navigation_fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prabin.restaurant.R;
import com.example.prabin.restaurant.adapter.FoodMenuPagerAdapter;
import com.example.prabin.restaurant.adapter.MenuItemListRVAdapter;
import com.example.prabin.restaurant.modal.MenuItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodMenuFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    ArrayList<MenuItem> mMenuList;
    RecyclerView mRecyclerView;
    MenuItemListRVAdapter mAdapter;

    public FoodMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        try {
            mMenuList = (ArrayList<MenuItem>) args.getSerializable(ARG_OBJECT);

            mRecyclerView = view.findViewById(R.id.food_menu_rvList);
            mAdapter = new MenuItemListRVAdapter(getContext(), mMenuList);

//        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(llManager);

            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        } catch (Exception e) {

        }
    }

}
