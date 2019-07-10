package com.example.prabin.restaurant.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.prabin.restaurant.modal.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
//    tableNumber, time, items, quantity, packing, remarks, kitchenProcess, chefName, completed

    public static final String DB_NAME = "DB_RESTAURANT_ORDER";
    public static final String TABLE_NAME = "tbl_orders";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TABLE_NUMBER = "table_number";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PACKING = "packing";
    public static final String COLUMN_REMARKS = "remarks";
    public static final String COLUMN_KITCHEN_PROCESS = "kitchen_process";
    public static final String COLUMN_CHEF_NAME = "chef_name";
    public static final String COLUMN_COMPLETED = "completed";

    private static final int DB_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TABLE_NUMBER + " VARCHAR, "
                + COLUMN_TIME + " VARCHAR, "
                + COLUMN_ITEMS + " VARCHAR, "
                + COLUMN_QUANTITY + " VARCHAR, "
                + COLUMN_PACKING + " VARCHAR, "
                + COLUMN_REMARKS + " VARCHAR, "
                + COLUMN_KITCHEN_PROCESS + " VARCHAR, "
                + COLUMN_CHEF_NAME + " VARCHAR, "
                + COLUMN_COMPLETED +" VARCHAR);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /*
        0 - in sync with server
        1 - not synced with server
    */
    public boolean addNewOrder(OrderItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TABLE_NUMBER, item.getTableNumber());
        contentValues.put(COLUMN_TIME, item.getTime());
        contentValues.put(COLUMN_ITEMS, item.getItems());
        contentValues.put(COLUMN_QUANTITY, item.getQuantity());
        contentValues.put(COLUMN_PACKING, item.getPacking());
        contentValues.put(COLUMN_REMARKS, item.getRemarks());
        contentValues.put(COLUMN_KITCHEN_PROCESS, item.getKitchenProcess());
        contentValues.put(COLUMN_CHEF_NAME, item.getChefName());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }


    public List<OrderItem> getOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC;";
        Cursor cursor = db.rawQuery(sql, null);

        List<OrderItem> items = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
        Log.d("table", cursor.getString(cursor.getColumnIndex(COLUMN_PACKING)));
                OrderItem item = new OrderItem(
                        cursor.getString(cursor.getColumnIndex(COLUMN_TABLE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ITEMS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PACKING)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_REMARKS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_KITCHEN_PROCESS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CHEF_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
}
