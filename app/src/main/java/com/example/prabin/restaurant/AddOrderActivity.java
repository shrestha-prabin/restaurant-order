package com.example.prabin.restaurant;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    Button btnAddOrder;

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


        btnAddOrder = findViewById(R.id.add_order_btnSubmit);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrder();
            }
        });

    }

    private void submitOrder() {
        final PrefManager prefManager = new PrefManager(this);
        final String orderItemName = prefManager.getOrderName();
        final int orderItemCount = prefManager.getOrderCount();
        final int orderItemPrice = prefManager.getOrderPriceRate();

        if (orderItemName.equals("") || orderItemCount == 0) {
            Toast.makeText(this, "Select an item first.", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_order, null);

        final TextView itemName, itemQuantity, itemPrice;
        final EditText eTableNumber, etRemarks;
        Button btnConfirm, btnCancel;
        final Spinner spinPackingType, spinPackingLevel;

        itemName = dialogView.findViewById(R.id.add_order_tvItemName);
        itemQuantity = dialogView.findViewById(R.id.add_order_tvItemQuantity);
        itemPrice = dialogView.findViewById(R.id.add_order_tvItemPrice);

        eTableNumber = dialogView.findViewById(R.id.add_order_etTableNo);
        etRemarks = dialogView.findViewById(R.id.add_order_etRemarks);

        spinPackingType = dialogView.findViewById(R.id.add_order_spinPackingType);
        spinPackingLevel = dialogView.findViewById(R.id.add_order_spinPackingLevel);

        btnConfirm = dialogView.findViewById(R.id.add_order_btnConfirm);
        btnCancel = dialogView.findViewById(R.id.add_order_btnCancel);

        itemName.setText(orderItemName);
        itemQuantity.setText(String.valueOf(orderItemCount));
        itemPrice.setText("Rs. " + (orderItemCount * orderItemPrice) + " (@Rs." + orderItemPrice + ")");

        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(eTableNumber.getText())) {
                    Toast.makeText(AddOrderActivity.this, "Table number is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tableNumber = eTableNumber.getText().toString();
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
                        Toast.makeText(AddOrderActivity.this, "Order Added", Toast.LENGTH_SHORT).show();
//                finish();
                        alertDialogBuilder.dismiss();
//               dbHelper.addNewOrder(orderItem);
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder.dismiss();
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

        beverageList.add(new MenuItem("http://picsum.photos/300", "Tea", "Item description/types", 10));
        beverageList.add(new MenuItem("http://picsum.photos/350", "Coffee", "Item description/types", 20));
        beverageList.add(new MenuItem("http://picsum.photos/500", "Milk", "Item description/types", 30));
        beverageList.add(new MenuItem("http://picsum.photos/800", "Tea", "Item description/types", 40));
        beverageList.add(new MenuItem("http://picsum.photos/150", "Coffee", "Item description/types", 50));
        beverageList.add(new MenuItem("http://picsum.photos/600", "Milk", "Item description/types", 100));
        beverageList.add(new MenuItem("http://picsum.photos/650", "Juice", "Item description/types", 100));

        breakfastList.add(new MenuItem("http://picsum.photos/220", "Omelet", "Item description/types", 100));
        breakfastList.add(new MenuItem("http://picsum.photos/230", "Hard-boiled Eggs", "Item description/types", 100));

        bakeryIcecreamList.add(new MenuItem("http://picsum.photos/240", "Bread", "Item description/types", 100));
        bakeryIcecreamList.add(new MenuItem("http://picsum.photos/260", "Cake", "Item description/types", 100));
        bakeryIcecreamList.add(new MenuItem("http://picsum.photos/280", "Ice-cream", "Item description/types", 100));

        lunchList.add(new MenuItem("http://picsum.photos/310", "Mo:Mo", "Item description/types", 100));

        snacksList.add(new MenuItem("http://picsum.photos/320", "Soup", "Item description/types", 100));

        dinnerList.add(new MenuItem("http://picsum.photos/330", "Rice", "Item description/types", 100));

        softDrinkList.add(new MenuItem("http://picsum.photos/340", "Wine", "Item description/types", 100));
        softDrinkList.add(new MenuItem("http://picsum.photos/350", "Beer", "Item description/types", 100));

        hardDrinkList.add(new MenuItem("http://picsum.photos/360", "Whiskey", "Item description/types", 100));
        hardDrinkList.add(new MenuItem("http://picsum.photos/400", "Vodka", "Item description/types", 100));

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
