package com.example.prabin.restaurant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prabin.restaurant.helper.DatabaseHelper;
import com.example.prabin.restaurant.modal.OrderItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOrderActivity extends AppCompatActivity {

    Spinner spinPackingType, spinPackingLevel;
    EditText etTableNumber, etItems, etQuantity, etRemarks;
    Button btnSubmit, btnCancel;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        spinPackingType = findViewById(R.id.add_spinPackingType);
        spinPackingLevel = findViewById(R.id.add_spinPackingLevel);
        etTableNumber = findViewById(R.id.add_etTableNumber);
        etItems = findViewById(R.id.add_etItems);
        etQuantity = findViewById(R.id.add_etQuantity);
        etRemarks = findViewById(R.id.add_etRemarks);

        btnSubmit = findViewById(R.id.add_btnSubmit);
        btnCancel = findViewById(R.id.add_btnCancel);

        dbHelper = new DatabaseHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etTableNumber.getText())
                        || TextUtils.isEmpty(etItems.getText())
                        || TextUtils.isEmpty(etQuantity.getText())
                        || TextUtils.isEmpty(etRemarks.getText())) {
                    Toast.makeText(AddOrderActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitOrder();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrderEntry();
            }
        });

    }

    private void submitOrder() {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");

        String tableNumber = etTableNumber.getText().toString();
        String items = etItems.getText().toString();
        String quantity = etQuantity.getText().toString();
        String remarks = etRemarks.getText().toString();

        String[] packingTypes = getResources().getStringArray(R.array.packingType);
        String[] packingLevels = getResources().getStringArray(R.array.packingLevels);

        String packing = packingTypes[spinPackingType.getSelectedItemPosition()]
                + " - " + packingLevels[spinPackingLevel.getSelectedItemPosition()];
        String time = getTime();

        final OrderItem orderItem  = new OrderItem();
        orderItem.setTableNumber(tableNumber);
        orderItem.setTime(time);
        orderItem.setItems(items);
        orderItem.setQuantity(quantity);
        orderItem.setRemarks(remarks);
        orderItem.setPacking(packing);
        orderItem.setKitchenProcess("Order");
        orderItem.setChefName("-");
        orderItem.setCompleted("0");

       orderRef.push().setValue(orderItem).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Toast.makeText(AddOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
               finish();
//               dbHelper.addNewOrder(orderItem);
           }
       });
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
