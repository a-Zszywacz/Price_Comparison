package com.example.price_comparison.api;

public class Store {
    private int id;
    private String storeName;

    public Store(int id, String storeName){
        this.id = id;
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
