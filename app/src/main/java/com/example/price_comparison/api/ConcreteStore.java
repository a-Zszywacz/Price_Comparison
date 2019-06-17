package com.example.price_comparison.api;

/**
 * Klasa służąca do zarządzania lokalizacją sklepu w bazie danych.
 */
public class ConcreteStore extends Store {
    private String city; /**< \brief Miasto */
    private String postCode; /**< \brief Kod pocztowy */
    private String street; /**< \brief Ulica  */

    /**
     * \brief Konstruktor parametryczny tworzący lokalizację sklepu na podstawie ID, Nazwy, Miasta, Kodu pocztowego, Ulicy.
     * Konstruktor parametryczny tworzący lokalizację sklepu na podstawie ID, Nazwy, Miasta, Kodu pocztowego, Ulicy.
     * @param id ID sklepu
     * @param name Nazwa Sklepu
     * @param city Miasto
     * @param postCode Kod pocztowy
     * @param street Ulica
     */
    public ConcreteStore(int id, String name, String city, String postCode, String street)
    {
        super(id, name);
        this.city = city;
        this.postCode = postCode;
        this.street = street;
    }

    /**
     * \brief Metoda typu GET. Zwraca miasto, w którym znajduje się sklep.
     * Metoda typu GET. Zwraca miasto, w którym znajduje się sklep.
     * @return miasto
     */
    public String getCity() {
        return city;
    }

    /**
     * \brief Metoda typu GET. Zwraca kod pocztowy.
     * Metoda typu GET. Zwraca kod pocztowy.
     * @return kod pocztowy
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * \brief Metoda typu GET. Zwraca ulicę na której znajduje się sklep.
     * Metoda typu GET. Zwraca ulicę na której znajduje się sklep.
     * @return ulica
     */
    public String getStreet() {
        return street;
    }

    /**
     * \brief Metoda typu SET. Ustawia miasto dla danego sklepu.
     * Metoda typu SET. Ustawia miasto dla danego sklepu.
     * @param city miasto
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * \brief Metoda typu SET. Ustawia kod pocztowy dla danego sklepu.
     * Metoda typu SET. Ustawia kod pocztowy dla danego sklepu.
     * @param postCode kod pocztowy
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * \brief Metoda typu SET. Ustawia ulicę dla danego sklepu.
     * Metoda typu SET. Ustawia ulicę dla danego sklepu.
     * @param street ulica
     */
    public void setStreet(String street) {
        this.street = street;
    }
}
