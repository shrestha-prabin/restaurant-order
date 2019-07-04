package com.example.prabin.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.modal.User;
import com.example.prabin.restaurant.navigation_fragments.HomeFragment;
import com.example.prabin.restaurant.navigation_fragments.TransactionTableFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    Button btnSignout;
    TextView tvUsername, tvEmail, tvRole;

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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        loadHomeFragment();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_admin_home:
                loadHomeFragment();
                break;

            case R.id.nav_admin_transactions:
                loadTableFragment();
                break;

            case R.id.nav_admin_users:
                break;

            case R.id.nav_admin_menu:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_main, homeFragment);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentTransaction.commit();
            }
        },250);
    }

    private void loadTableFragment() {
        TransactionTableFragment transactionTableFragment = new TransactionTableFragment();
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_main, transactionTableFragment);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentTransaction.commit();
            }
        },250);
    }
}
