package com.example.price_comparison.api;

public class Product {
    private int id;
    private String name;
    private String producerCode;

    public Product(int id, String name, String producerCode)
    {
        this.id = id;
        this.name = name;
        this.producerCode = producerCode;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }


}
