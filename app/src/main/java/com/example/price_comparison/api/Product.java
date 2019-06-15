package com.example.price_comparison.api;

/**
 * Klasa służąca do zarządzania produktami w bazie danych.
 */
public class Product {
    private int id;  /**< ID Produktu */
    private String name;  /**< Nazwa produktu */
    private String producerCode;  /**< Kod producenta */

    /**
     * /brief Konstruktor parametryczny tworzący produkt na podstawie ID, nazwy, kodu producenta.
     * Konstruktor parametryczny tworzący produkt na podstawie ID, nazwy, kodu producenta.
     * @param id ID Produktu
     * @param name Nazwa produktu
     * @param producerCode Kod producenta
     */
    public Product(int id, String name, String producerCode)
    {
        this.id = id;
        this.name = name;
        this.producerCode = producerCode;
    }

    /**
     * /brief Metoda typu GET. Zwraca ID Produktu.
     * Metoda typu GET. Zwraca ID Produktu.
     * @return ID Produktu.
     */
    public int getId() {
        return id;
    }

    /**
     * /brief Metoda typu SET. Ustawia ID produktu.
     * Metoda typu SET. Ustawia ID produktu.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * /brief Metoda typu GET. Zwraca Nazwe Produktu.
     * Metoda typu GET. Zwraca Nazwe Produktu.
     * @return Nazwa Produktu.
     */
    public String getName() {
        return name;
    }

    /**
     * /brief Metoda typu SET. Ustawia nazwe produktu.
     * Metoda typu SET. Ustawia nazwe produktu.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * /brief Metoda typu GET. Zwraca Kod producenta produktu.
     * Metoda typu GET. Zwraca Kod producenta produktu.
     * @return Kod producenta Produktu.
     */
    public String getProducerCode() {
        return producerCode;
    }

    /**
     * /brief Metoda typu SET. Ustawia kod producenta produktu.
     * Metoda typu SET. Ustawia kod producenta produktu.
     * @param producerCode
     */
    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }


}
