package com.example.prabin.restaurant;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.modal.User;
import com.example.prabin.restaurant.navigation_fragments.TransactionTableFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btnLogout, btnAddOrder;
    TextView tvUsername, tvEmail, tvPhone, tvRole, tvIsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btnLogout);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvRole = findViewById(R.id.tvRole);
        tvIsEnabled = findViewById(R.id.tvIsEnabled);

        btnAddOrder = findViewById(R.id.btnAddOrder);

        User user = new PrefManager(this).getUserDetails();
        tvUsername.setText(user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhoneNumber());
        tvRole.setText(user.getRole());
        tvIsEnabled.setText(user.isEnabled() ? "Enabled" : "Disabled");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
            }
        });

        loadTableFragment();

    }

    private void loadTableFragment() {
        TransactionTableFragment transactionTableFragment = new TransactionTableFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameTable, transactionTableFragment);
        fragmentTransaction.commit();
    }

    void signOutUser() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    void addOrder() {
        startActivity(new Intent(MainActivity.this, AddOrderActivity.class));
    }

}
