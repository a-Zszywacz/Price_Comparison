package com.example.price_comparison.customlist;

/**
 * Klasa stworzona @TODO: dopisać
 */
public class SingleStore {
    String name; /**< Nazwa produktu */
    Double price; /**< Cena produktu */
    /**
     * /brief Konstruktor parametryczny, tworzący pojedynczy sklep na podstawie nazwy, oraz ceny produktu.
     * Konstruktor parametryczny, tworzący pojedynczy sklep na podstawie nazwy, oraz ceny produktu.
     * @param name
     * @param price
     */
    public SingleStore(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * /brief Metoda typu GET. Zwraca nazwę produktu.
     * Metoda typu GET. Zwraca nazwę produktu.
     * @return nazwa
     */
    public String getName() {
        return name;
    }

    /**
     * /brief Metoda typu GET. Zwraca cenę produktu.
     * Metoda typu GET. Zwraca cenę produktu.
     * @return cena
     */
    public Double getPrice() {
        return price;
    }
}
