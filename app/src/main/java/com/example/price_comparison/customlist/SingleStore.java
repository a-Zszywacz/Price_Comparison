package com.example.price_comparison.customlist;

/**
 * Klasa stworzona w celu wyświetlenia cen produktów w sklepach, które zostały uznane za drugorzędne.
 */
public class SingleStore {
    String name; /**< \brief Nazwa sklepu*/
    Double price; /**< \brief Cena produktu */
    /**
     * \brief Konstruktor parametryczny, tworzący pojedynczy sklep na podstawie nazwy, oraz ceny produktu.
     * Konstruktor parametryczny, tworzący pojedynczy sklep na podstawie nazwy, oraz ceny produktu.
     * @param name nazwa sklepu
     * @param price cena produktu w sklepie
     */
    public SingleStore(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * \brief Metoda typu GET. Zwraca nazwę sklepu.
     * Metoda typu GET. Zwraca nazwę sklepu.
     * @return nazwa
     */
    public String getName() {
        return name;
    }

    /**
     * \brief Metoda typu GET. Zwraca cenę produktu.
     * Metoda typu GET. Zwraca cenę produktu.
     * @return cena
     */
    public Double getPrice() {
        return price;
    }
}
