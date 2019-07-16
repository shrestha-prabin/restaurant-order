package com.example.prabin.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.modal.User;
import com.example.prabin.restaurant.navigation_fragments.HomeFragment;
import com.example.prabin.restaurant.navigation_fragments.TransactionTableFragment;
import com.example.prabin.restaurant.navigation_fragments.manage.ManageMenuFragment;
import com.example.prabin.restaurant.navigation_fragments.manage.ManageUsersFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    Button btnSignout;
    TextView tvUsername, tvEmail, tvRole;

    private static final int MENU_TRANSACTION = 1001;
    private static final int MENU_USER = 1002;
    private static final int MENU_FOOD_MENU = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab_addOrder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, AddOrderActivity.class));

            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavigationView();

        View headerView = navigationView.getHeaderView(0);
        btnSignout = headerView.findViewById(R.id.profile_btnSignout);
        tvUsername = headerView.findViewById(R.id.profile_tvUsername);
        tvRole = headerView.findViewById(R.id.profile_tvRole);
        tvEmail = headerView.findViewById(R.id.profile_tvEmail);

        setUserDetails();

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signoutUser();
            }
        });

        loadFragment(new HomeFragment());
    }

    private void setupNavigationView() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        User user = new PrefManager(this).getUserDetails();
        switch (user.getRole().toLowerCase()) {
            case "admin":
                menu.add(R.id.nav_group_top, MENU_TRANSACTION, 0, "Transactions")
                        .setIcon(R.drawable.ic_view_list_black_24dp);

                menu.add(R.id.nav_group_manage, MENU_USER, Menu.NONE, "Users")
                        .setIcon(R.drawable.ic_group_black_24dp);
                menu.add(R.id.nav_group_manage, MENU_FOOD_MENU, Menu.NONE, "Food Menu")
                        .setIcon(R.drawable.ic_restaurant_menu_black_24dp);
                break;
            case "counter":
                menu.add(R.id.nav_group_top, MENU_TRANSACTION, 0, "Transactions")
                        .setIcon(R.drawable.ic_view_list_black_24dp);
                menu.add(R.id.nav_group_manage, MENU_FOOD_MENU, 0, "Food Menu")
                        .setIcon(R.drawable.ic_restaurant_menu_black_24dp);
                break;
            case "chef":
                menu.add(R.id.nav_group_top, MENU_TRANSACTION, 0, "Transactions")
                        .setIcon(R.drawable.ic_view_list_black_24dp);
                break;
        }

        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
            final View child = navigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }
    }

    private void signoutUser() {
        mAuth.signOut();
        startActivity(new Intent(Main2Activity.this, LoginActivity.class));
        finish();
    }

    private void setUserDetails() {
        User user = new PrefManager(this).getUserDetails();
        tvUsername.setText(user.getFirstName() + " " + user.getLastName());
        tvRole.setText(user.getRole());
        tvEmail.setText(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                loadFragment(new HomeFragment());
                break;

            case MENU_TRANSACTION:
               loadFragment(new TransactionTableFragment());
                break;

            case MENU_USER:
                loadFragment(new ManageUsersFragment());
                break;

            case MENU_FOOD_MENU:
                loadFragment(new ManageMenuFragment());
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_main, fragment);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentTransaction.commit();
            }
        }, 250);
    }
}
