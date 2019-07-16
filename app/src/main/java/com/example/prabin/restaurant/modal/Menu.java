package com.example.prabin.restaurant.modal;

import java.util.List;

public class Menu {
    private String category;
    private List<MenuItem> itemList;

    public Menu(String category, List<MenuItem> itemList) {
        this.category = category;
        this.itemList = itemList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<MenuItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<MenuItem> itemList) {
        this.itemList = itemList;
    }
}
