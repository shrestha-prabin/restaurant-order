package com.example.prabin.restaurant.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.prabin.restaurant.modal.User;


/**
 * Created by Prabin on 3/17/2019.
 */

public class PrefManager {
    private Context mContext;

    private static final String PREF_USER = "USER_DETAILS";
    private static final String PREF_FIRST_NAME = "FIRST_NAME";
    private static final String PREF_LAST_NAME = "LAST_NAME";
    private static final String PREF_EMAIL = "USER_EMAIL";
    private static final String PREF_PHONE = "USER_PHONE";
    private static final String PREF_ROLE = "USER_ROLE";
    private static final String PREF_ENABLED = "USER_ENABLED";

    private static final String PREF_ORDER_CART = "ORDER";
    private static final String PREF_ORDER_CART_ITEM = "ORDER_ITEM";
    private static final String PREF_ORDER_CART_COUNT = "ORDER_ITEM_COUNT";

    public PrefManager(Context context) {
        mContext = context;
    }

    public void saveUserDetails(User user) {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_FIRST_NAME, user.getFirstName());
        editor.putString(PREF_LAST_NAME, user.getLastName());
        editor.putString(PREF_EMAIL, user.getEmail());
        editor.putString(PREF_PHONE, user.getPhoneNumber());
        editor.putString(PREF_ROLE, user.getRole());
        editor.putBoolean(PREF_ENABLED, user.isEnabled());
        editor.apply();
    }

    public User getUserDetails() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        String firstName = sharedPrefs.getString(PREF_FIRST_NAME, "");
        String lastName = sharedPrefs.getString(PREF_LAST_NAME, "");
        String email = sharedPrefs.getString(PREF_EMAIL, "");
        String phone = sharedPrefs.getString(PREF_PHONE, "");
        String role = sharedPrefs.getString(PREF_ROLE, "");
        boolean enabled = sharedPrefs.getBoolean(PREF_ENABLED, false);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setRole(role);
        user.setEnabled(enabled);

        return user;
    }

    public void clearUserDetails() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_FIRST_NAME, "");
        editor.putString(PREF_LAST_NAME, "");
        editor.putString(PREF_EMAIL, "");
        editor.putString(PREF_PHONE, "");
        editor.putString(PREF_ROLE, "");
        editor.putBoolean(PREF_ENABLED, false);
        editor.apply();
    }

    public void setOrderItemName(String itemName) {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_ORDER_CART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(PREF_ORDER_CART_ITEM, itemName);

        editor.apply();
    }

    public void setOrderItemCount(int count) {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_ORDER_CART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putInt(PREF_ORDER_CART_COUNT, count);

        editor.apply();
    }

    public String getOrderName() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_ORDER_CART, Context.MODE_PRIVATE);
        return sharedPrefs.getString(PREF_ORDER_CART_ITEM, "");
    }
    public int getOrderCount() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_ORDER_CART, Context.MODE_PRIVATE);
        return sharedPrefs.getInt(PREF_ORDER_CART_COUNT, 0);
    }

    public void clearOrderDetails() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences(PREF_ORDER_CART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_ORDER_CART_ITEM, "");
        editor.putInt(PREF_ORDER_CART_COUNT, 0);
        editor.apply();
    }
}
