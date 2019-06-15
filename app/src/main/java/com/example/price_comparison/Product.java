package com.example.price_comparison;

import android.os.Parcel;
import android.os.Parcelable;
/**
 *  Klasa przechowująca szczegóły produktów. Implementuje interfejs Parceable.
 */
public class Product implements Parcelable {

    private String code; /**< Kod produktu */
    private String name; /**< Nszwa produktu */
    private Double price; /**< Cena produktu */
    private String storeName; /**< Nazwa sklepu, w którym znajduje się produkt */

    /** \brief Konstruktor parametryczny tworzący produkt na podstawie kodu, nazwy, ceny, nazwy sklepu.
     *
     * @param code Kod produktu
     * @param name Nazwa produktu
     * @param price Cena produktu
     * @param storeName Nazwa sklepu, w którym znajduje się produkt
     */
    public Product(String code, String name, Double price, String storeName) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.storeName = storeName;
    }

    /**
     * \brief Metoda służąca do ustawiania wartości wszystkich pól produktu.
     * Metoda służąca do ustawiania wartości wszystkich pól produktu.
     *
     * @param code Kod produktu
     * @param name Nazwa produktu
     * @param price Cena produktu
     * @param storeName Nazwa sklepu, w którym znajduje się produkt
     */
    public void setAll(String code, String name, Double price, String storeName) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.storeName = storeName;
    }

    /**
     * \brief Metoda opisuje rodzaje obiektów specjalnych zawartych w reprezentacji rozproszonej instacji Parceable.
     * Metoda opisuje rodzaje obiektów specjalnych zawartych w reprezentacji rozproszonej instacji Parceable.
     * @return CONTENTS_FILE_DESCRIPTOR. Wskazuje, że reprezentacja obiektu Parcelable zawiera deskryptor pliku.
     */
    public int describeContents() {
        return 0;
    }

    /**
     * \brief Metoda zapisująca dane produktu do paczki.
     * Metoda zapisująca dane produktu do paczki.
     * @param out wyjście
     * @param flags flaga
     */
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(code);
        out.writeString(name);
        out.writeDouble(price);
        out.writeString(storeName);
    }

    /**
     * \brief Metoda wczytująca dane produktu.
     * Metoda wczytująca dane produktu.
     * @param in wejście
     */
    private Product(Parcel in) {
        in.writeString(code);
        in.writeString(name);
        in.writeDouble(price);
        in.writeString(storeName);
    }

    /**
     * Interfejs, który musi zostać zaimplementowany i udostępniony jako publiczne pole CREATOR, które generuje instancje klasy Parcelable.
     */
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        /**
         * Tworzy nową instancję klasy Parcelable, tworząc instancję z podanej Parcel, której dane zostały wcześniej zapisane.
         * @param in wejście
         * @return paczka z produktem
         */
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        /**
         * Tworzy nową tablicę klasy Praceable.
         * @param size rozmiar
         * @return utworzona tablica
         */
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**
     * \brief Metoda typu GET zwracająca kod produktu.
     * Metoda typu GET zwracająca kod produktu.
     * @return kod produktu
     */
    public String getCode() {
        return code;
    }

    /**
     * \brief Metoda typu SET ustawiająca kod produktu.
     * Metoda typu SET ustawiająca kod produktu.
     * @param code kod produktu
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * \brief Metoda typu GET zwracająca nazwe produktu.
     * Metoda typu GET zwracająca nazwe produktu.
     * @return nazwa produktu
     */
    public String getName() {
        return name;
    }

    /**
     * \brief Metoda typu SET ustawiająca nazwe produktu.
     * Metoda typu SET ustawiająca nazwe produktu.
     * @param name nazwa produktu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * \brief Metoda typu GET zwracająca cene produktu.
     * Metoda typu GET zwracająca cene produktu.
     * @return cena produktu
     */
    public Double getPrice() {
        return price;
    }

    /**
     * \brief Metoda typu SET ustawiająca cene produktu.
     * Metoda typu SET ustawiająca cene produktu.
     * @param price cena produktu
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * \brief Metoda typu GET zwracająca nazwe sklepu.
     * Metoda typu GET zwracająca nazwe sklepu.
     * @return nazwa sklepu
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * \brief  Metoda typu SET ustawiająca nazwe sklepu.
     * Metoda typu SET ustawiająca nazwe sklepu.
     * @param storeName nazwa sklepu
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
