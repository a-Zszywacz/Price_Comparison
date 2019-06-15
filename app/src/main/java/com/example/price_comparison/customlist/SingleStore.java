package com.example.price_comparison.customlist;

public class SingleStore {
    String name;
    Double price;

    public SingleStore(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
