package com.example.price_comparison.customlist;

public class SingleStore {
    String name;
    String price;

    public SingleStore(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
