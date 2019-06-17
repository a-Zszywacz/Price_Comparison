package com.example.price_comparison.api;

/**
 * Klasa służąca do zarządzania sklepami w bazie danych.
 */
public class Store {
    private int id; /**< \brief ID sklepu */
    private String storeName; /**< \brief Nazwa sklepu */

    /**
     * \brief Konstruktor parametryczny, tworzący sklep na podstawie ID, oraz nazwy.
     * Konstruktor parametryczny, tworzący sklep na podstawie ID, oraz nazwy.
     * @param id ID sklepu
     * @param storeName Nazwa sklepu
     */
    public Store(int id, String storeName){
        this.id = id;
        this.storeName = storeName;
    }

    /**
     * Metoda typu GET. Zwraca ID sklepu.
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Metoday typu SET. Ustawia wartość ID sklepu.
     * @param id ID sklepu
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Metoda typu GET. Zwraca nazwę sklepu.
     * @return Nazwa sklepu
     */
    public String getStoreName() {
        return storeName;
    }
    /**
     * Metoday typu SET. Ustawia wartość nazwę sklepu.
     * @param storeName nazwa sklepu
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
