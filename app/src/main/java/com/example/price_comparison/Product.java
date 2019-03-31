package com.example.price_comparison;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String code;
    private String name;
    private Double price;
    private String storeName;

    public Product(String code, String name, Double price, String storeName) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.storeName = storeName;
    }

    public void setAll(String code, String name, Double price, String storeName) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.storeName = storeName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(code);
        out.writeString(name);
        out.writeDouble(price);
        out.writeString(storeName);
    }

    private Product(Parcel in) {
        in.writeString(code);
        in.writeString(name);
        in.writeDouble(price);
        in.writeString(storeName);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
