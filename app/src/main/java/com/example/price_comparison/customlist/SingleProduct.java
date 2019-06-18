package com.example.price_comparison.customlist;

import java.io.Serializable;

public class SingleProduct implements Serializable {
    /*"(_id integer primary key autoincrement,"+
                "name text not null,"+
                "price text not null,"+
                "shop text not null,"+
                "address text not null);";*/

    private int _id;
    private String name;
    private String price;
    private String shop;
    private String address;

    public SingleProduct() {
    }

    public SingleProduct(String name, String price, String shop, String address) {
        super();
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.address = address;
    }

    //@RecentlyNonNull
    @Override
    public String toString() {
        return "SingleProduct: [id-"+_id+", name-"+name+", price-"+price+", shop-"+shop+", address-"+address+" ]";
    }

    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getShop() {
        return shop;
    }

    public String getAddress() {
        return address;
    }
}
