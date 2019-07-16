package com.example.prabin.restaurant.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.prabin.restaurant.modal.Menu;
import com.example.prabin.restaurant.modal.MenuItem;
import com.example.prabin.restaurant.navigation_fragments.FoodMenuFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodMenuPagerAdapter extends FragmentStatePagerAdapter {

    private List<Menu> mMenuList;

    public FoodMenuPagerAdapter(FragmentManager fm, List<Menu> menuList) {
        super(fm);
        this.mMenuList = menuList;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FoodMenuFragment();
        Bundle args = new Bundle();

        args.putSerializable(FoodMenuFragment.ARG_OBJECT, (Serializable) mMenuList.get(i).getItemList());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mMenuList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mMenuList.get(position).getCategory();
    }
}
