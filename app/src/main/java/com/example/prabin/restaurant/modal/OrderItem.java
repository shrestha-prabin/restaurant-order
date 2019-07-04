package com.example.prabin.restaurant.modal;

public class OrderItem {

    private String tableNumber, time, items, quantity, packing, remarks, kitchenProcess, chefName, completed;

    public OrderItem() {
    }

    public OrderItem(String tableNumber, String time, String items, String quantity, String packing, String remarks, String kitchenProcess, String chefName, String completed) {
        this.tableNumber = tableNumber;
        this.time = time;
        this.items = items;
        this.quantity = quantity;
        this.packing = packing;
        this.remarks = remarks;
        this.kitchenProcess = kitchenProcess;
        this.chefName = chefName;
        this.completed = completed;
    }


    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getKitchenProcess() {
        return kitchenProcess;
    }

    public void setKitchenProcess(String kitchenProcess) {
        this.kitchenProcess = kitchenProcess;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
