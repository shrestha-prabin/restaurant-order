package com.example.prabin.restaurant.navigation_fragments.manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prabin.restaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageUsersFragment extends Fragment {


    public ManageUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_users, container, false);
    }

}
