package com.example.price_comparison.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa służąca do zarządzania produktami w konkretnym sklepie.
 */
public class ProductInStore extends Product {
    private String currency; /**< \brief Waluta */
    private float price; /**< \brief Cena produktu */
    private String durationOfPromotionFrom; /**< \brief Czas trwania promocji OD */
    private String durationOfPromotionTo; /**< \brief Czas trwania promocji DO */

    private List<ConcreteStore> stores; /**< \brief Lista sklepów */
    /**
     * \brief Konstruktor parametryczny tworzący produkt w sklepie na podstawie ID, nazwy, kodu, ilości, ceny, czasu promocji.
     * Konstruktor parametryczny tworzący produkt w sklepie na podstawie ID, nazwy, kodu, ilości, ceny, czasu promocji.
     * @param id ID produktu
     * @param name Nazwa produktu
     * @param producerCode Kod producenta
     * @param currency  Waluta
     * @param price Cena produktu
     * @param durationOfPromotionFrom Czas trwania promocji OD
     * @param durationOfPromotionTo Czas trwania promocji DO
     */
    public ProductInStore(int id, String name, String producerCode, String currency, float price, String durationOfPromotionFrom, String durationOfPromotionTo)
    {
        super(id,name,producerCode);
        this.currency = currency;
        this.price = price;
        this.durationOfPromotionFrom = durationOfPromotionFrom;
        this.durationOfPromotionTo = durationOfPromotionTo;
        this.stores = new ArrayList<ConcreteStore>();
    }

    /**
     * \brief Pobiera listę sklepów.
     * Pobiera listę sklepów.
     * @return lista sklepów
     */
    public List<ConcreteStore> getStores() {
        return stores;
    }

    /**
     * \brief Dodaje sklepy do listy sklepów.
     * Dodaje sklepy do listy sklepów.
     * @param stores lista sklepów
     */
    public void addStores(List<ConcreteStore> stores) {
        this.stores = stores;
    }

    /**
     * \brief Dodaje sklep do listy sklepów.
     * Dodaje sklep do listy sklepów.
     * @param store sklep
     */
    public void addStore(ConcreteStore store)
    {
        this.stores.add(store);
    }

    /**
     * \brief Pobiera cene produktu w danym sklepie.
     * Pobiera cene produktu w danym sklepie.
     * @return cena produktu
     */
    public float getPrice() {
        return price;
    }

    /**
     * \brief Pobiera walute produktu w danym sklepie.
     * Pobiera walute produktu w danym sklepie.
     * @return waluta
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * \brief Pobiera czas rozpoczęcia promocji w danym sklepie.
     * Pobiera czas rozpoczęcia promocji.
     * @return czas rozpoczęcia promocji
     */
    public String getDurationOfPromotionFrom() {
        return durationOfPromotionFrom;
    }

    /**
     * \brief Pobiera czas zakończenie promocji w danym sklepie.
     * Pobiera czas zakończenie promocji.
     * @return czas zakończenia promocji
     */
    public String getDurationOfPromotionTo() {
        return durationOfPromotionTo;
    }

    /**
     * \brief Ustawia walutę produktu w danym sklepie.
     * Ustawia walutę produktu w danym sklepie.
     * @param currency waluta
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * \brief Ustawia czas rozpoczęcia promocji produktu w danym sklepie.
     * Ustawia czas rozpoczęcia promocji produktu w danym sklepie.
     * @param durationOfPromotionFrom czas rozpoczęcia promocji
     */
    public void setDurationOfPromotionFrom(String durationOfPromotionFrom) {
        this.durationOfPromotionFrom = durationOfPromotionFrom;
    }

    /**
     * \brief Ustawia czas zakończenia promocji produktu w danym sklepie.
     * Ustawia czas zakończenia promocji produktu w danym sklepie.
     * @param durationOfPromotionTo czas zakończenia promocji
     */
    public void setDurationOfPromotionTo(String durationOfPromotionTo) {
        this.durationOfPromotionTo = durationOfPromotionTo;
    }

    /**
     * \brief Ustawia cenę produktu w danym sklepie.
     * Ustawia cenę produktu w danym sklepie.
     * @param price cena
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
