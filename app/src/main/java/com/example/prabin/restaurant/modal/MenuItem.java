package com.example.prabin.restaurant.modal;

public class MenuItem {
    private String category, image, name, description, price;
    private boolean available;
    private int orderCount;

    public MenuItem() {
    }

    public MenuItem(String name) {
        this.category = "";
        this.image = "";
        this.name = name;
        this.description = "";
        this.price = "Rs. ";
        this.available = true;
        this.orderCount = 0;
    }

    public MenuItem(String image, String name, String description, String price) {
        this.category = "";
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = true;
        this.orderCount = 0;
    }

    public MenuItem(String category, String image, String name, String description, String price, boolean available, int orderCount) {
        this.category = category;
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.orderCount = orderCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
