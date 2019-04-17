package com.example.price_comparison.api;

import java.util.ArrayList;
import java.util.List;

public class ProductInStore extends Product {
    private String currency;
    private float price;
    private String durationOfPromotionFrom;
    private String durationOfPromotionTo;

    private List<ConcreteStore> stores;

    public ProductInStore(int id, String name, String producerCode, String currency, float price, String durationOfPromotionFrom, String durationOfPromotionTo)
    {
        super(id,name,producerCode);
        this.currency = currency;
        this.price = price;
        this.durationOfPromotionFrom = durationOfPromotionFrom;
        this.durationOfPromotionTo = durationOfPromotionTo;
        this.stores = new ArrayList<ConcreteStore>();
    }

    public List<ConcreteStore> getStores() {
        return stores;
    }

    public void addStores(List<ConcreteStore> stores) {
        this.stores = stores;
    }
    public void addStore(ConcreteStore store)
    {
        this.stores.add(store);
    }

    public float getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDurationOfPromotionFrom() {
        return durationOfPromotionFrom;
    }

    public String getDurationOfPromotionTo() {
        return durationOfPromotionTo;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDurationOfPromotionFrom(String durationOfPromotionFrom) {
        this.durationOfPromotionFrom = durationOfPromotionFrom;
    }

    public void setDurationOfPromotionTo(String durationOfPromotionTo) {
        this.durationOfPromotionTo = durationOfPromotionTo;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
