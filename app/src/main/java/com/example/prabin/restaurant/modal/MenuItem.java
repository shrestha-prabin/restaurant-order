package com.example.prabin.restaurant.modal;

public class MenuItem {
    private String name, category;
    private boolean available;
    private int orderCount;

    public MenuItem() {
    }

    public MenuItem(String name, boolean available) {
        this.name = name;
        this.category = "";
        this.available = available;
        this.orderCount = 0;
    }

    public MenuItem(String name, String category, boolean available) {
        this.name = name;
        this.category = category;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
