package com.example.prabin.restaurant;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prabin.restaurant.adapter.FoodMenuPagerAdapter;
import com.example.prabin.restaurant.helper.DatabaseHelper;
import com.example.prabin.restaurant.helper.PrefManager;
import com.example.prabin.restaurant.modal.Menu;
import com.example.prabin.restaurant.modal.MenuItem;
import com.example.prabin.restaurant.modal.OrderItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddOrderActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    FoodMenuPagerAdapter mFoodMenuPagerAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    List<Menu> menuList;

    Spinner spinPackingType, spinPackingLevel;
    Button btnAddOrder;
    EditText etTableNumber, etRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
//        getSupportActionBar().hide();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add New Order");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        menuList = new ArrayList<>();

        setMenuItems();
        new PrefManager(this).clearOrderDetails();

        mFoodMenuPagerAdapter = new FoodMenuPagerAdapter(getSupportFragmentManager(), menuList);
        mViewPager = findViewById(R.id.menu_viewpager);
        mViewPager.setAdapter(mFoodMenuPagerAdapter);

        mTabLayout = findViewById(R.id.menu_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        dbHelper = new DatabaseHelper(this);

        spinPackingType = findViewById(R.id.add_order_spinPackingType);
        spinPackingLevel = findViewById(R.id.add_order_spinPackingLevel);
        etTableNumber = findViewById(R.id.add_order_etTableNo);
        etRemarks = findViewById(R.id.add_order_etRemarks);

        btnAddOrder = findViewById(R.id.add_order_btnSubmit);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrder(view);
            }
        });

    }


    private void submitOrder(final View view) {
        final PrefManager prefManager = new PrefManager(this);
        String orderItemName = prefManager.getOrderName();
        int orderItemCount = prefManager.getOrderCount();

        if(orderItemName.equals("") || orderItemCount ==0) {
            Toast.makeText(this, "Select items first.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(etTableNumber.getText())) {
            Toast.makeText(this, "Table number is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        String tableNumber = etTableNumber.getText().toString();
        String remarks = etRemarks.getText().toString();

        String[] packingTypes = getResources().getStringArray(R.array.packingType);
        String[] packingLevels = getResources().getStringArray(R.array.packingLevels);

        String packing = packingTypes[spinPackingType.getSelectedItemPosition()]
                + " - " + packingLevels[spinPackingLevel.getSelectedItemPosition()];
        String time = getTime();

        final OrderItem orderItem = new OrderItem();
        orderItem.setTableNumber(tableNumber);
        orderItem.setTime(time);
        orderItem.setItems(orderItemName);
        orderItem.setQuantity(String.valueOf(orderItemCount));
        orderItem.setRemarks(remarks);
        orderItem.setPacking(packing);
        orderItem.setKitchenProcess("Order");
        orderItem.setChefName("-");
        orderItem.setCompleted("0");

        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");

        orderRef.push().setValue(orderItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Snackbar.make(view, "Order Placed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                prefManager.clearOrderDetails();
                finish();
//               dbHelper.addNewOrder(orderItem);
            }
        });

    }

    private void setMenuItems() {
        List<MenuItem> beverageList = new ArrayList<>();
        List<MenuItem> breakfastList = new ArrayList<>();
        List<MenuItem> bakeryIcecreamList = new ArrayList<>();
        List<MenuItem> lunchList = new ArrayList<>();
        List<MenuItem> snacksList = new ArrayList<>();
        List<MenuItem> dinnerList = new ArrayList<>();
        List<MenuItem> softDrinkList = new ArrayList<>();
        List<MenuItem> hardDrinkList = new ArrayList<>();

        beverageList.add(new MenuItem("Tea", true));
        beverageList.add(new MenuItem("Coffee", true));
        beverageList.add(new MenuItem("Milk", true));
        beverageList.add(new MenuItem("Juice", true));

        breakfastList.add(new MenuItem("Omelet", true));
        breakfastList.add(new MenuItem("Hard-boiled Eggs", true));

        bakeryIcecreamList.add(new MenuItem("Bread", true));
        bakeryIcecreamList.add(new MenuItem("Cake", true));
        bakeryIcecreamList.add(new MenuItem("Ice-cream", true));

        lunchList.add(new MenuItem("Mo:Mo", true));

        snacksList.add(new MenuItem("Soup", true));

        dinnerList.add(new MenuItem("Rice", true));

        softDrinkList.add(new MenuItem("Wine", true));
        softDrinkList.add(new MenuItem("Beer", true));

        hardDrinkList.add(new MenuItem("Whiskey", true));
        hardDrinkList.add(new MenuItem("Vodka", true));

        menuList.add(new Menu("Beverage", beverageList));
        menuList.add(new Menu("Breakfast", breakfastList));
        menuList.add(new Menu("Bakery & Ice-cream", bakeryIcecreamList));
        menuList.add(new Menu("Lunch", lunchList));
        menuList.add(new Menu("Snacks", snacksList));
        menuList.add(new Menu("Dinner", dinnerList));
        menuList.add(new Menu("Soft-Drinks", softDrinkList));
        menuList.add(new Menu("Hard-Drinks", hardDrinkList));
    }

    private void cancelOrderEntry() {
        finish();
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        return formatter.format(date);
    }

}
