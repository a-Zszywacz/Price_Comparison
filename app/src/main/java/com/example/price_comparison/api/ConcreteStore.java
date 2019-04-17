package com.example.price_comparison.api;

public class ConcreteStore extends Store {
    private String city;
    private String postCode;
    private String street;

    public ConcreteStore(int id, String name, String city, String postCode, String street)
    {
        super(id, name);
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
