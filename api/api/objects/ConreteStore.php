<?php

namespace PriceComparison;

include_once "../objects/Store.php";

/**
 * Klasa służąca do zarządzania lokalizacją sklepu w bazie danych.
 * @package PriceComparison
 */
Class ConcreteStore extends Store implements \JsonSerializable
{
    private $city; /**< \brief Miasto  */
    private $postCode;  /**< \brief Kod pocztowy  */
    private $street; /**< \brief Ulica  */

    private $products = array();

    /**
     * \brief Konstruktor parametryczny tworzący lokalizację sklepu na podstawie ID, Nazwy, Miasta, Kodu pocztowego, Ulicy.
     * Konstruktor parametryczny tworzący lokalizację sklepu na podstawie ID, Nazwy, Miasta, Kodu pocztowego, Ulicy.
     * @param null $id ID
     * @param $storeName Nazwa sklepu
     * @param $city Miasto
     * @param $postCode Kod pocztowy
     * @param $street Ulica
     */
    public function __construct($id = null, $storeName, $city, $postCode, $street)
    {
        parent::__construct($id, $storeName);

        if(is_string($city)){
            $this->city = $city;
        }
        if(is_string($postCode)){
            $this->postCode = $postCode;
        }
        if(is_string($street)){
            $this->street  = $street;
        }
    }

    /**
     * Funkcja służąca do dodawania produktu.
     * @param Product $product Produkt
     */
    public function addProduct(Product $product):void
    {
        array_push($this->products, $product);
    }

    /**
     * \brief Funkcja typu GET. Zwraca żądaną tablice z danymi produktu.
     * Funkcja typu GET. Zwraca żądaną tablice z danymi produktu.
     * @return array tablica
     */
    public function getProducts():array
    {
        return $this->produlcts;
    }

    /**
     * \brief Funkcja typu GET. Zwraca miasto, w którym znajduje się sklep.
     * Funkcja typu GET. Zwraca miasto, w którym znajduje się sklep.
     * @return string miasto
     */
    public function getCity(): string
    {
        return $this->city;
    }

    /**
     * \brief Funkcja typu SET. Ustawia miasto dla danego sklepu.
     * Funkcja typu SET. Ustawia miasto dla danego sklepu.
     * @param string $city miasto
     */
    public function setCity(string $city): void
    {
        $this->city = $city;
    }

    /**
     * \brief Funkcja typu GET. Zwraca kod pocztowy.
     * Funkcja typu GET. Zwraca kod pocztowy.
     * @return string kod pocztowy
     */
    public function getPostCode(): string
    {
        return $this->postCode;
    }

    /**
     * \brief Funkcja typu GET. Zwraca ulicę na której znajduje się sklep.
     * Funkcja typu GET. Zwraca ulicę na której znajduje się sklep.
     * @return string ulica
     */
    public function getStreet()
    {
        return $this->street;
    }

    /**
     * \brief Funkcja typu SET. Ustawia kod pocztowy dla danego sklepu.
     * Funkcja typu SET. Ustawia kod pocztowy dla danego sklepu.
     * @param string $postCode kod pocztowy
     */
    public function setPostCode(string $postCode): void
    {
        $this->postCode = $postCode;
    }

    /**
     * Funkcja przekazuje dane do pliku JSON.
     * @return array|mixed dane sklepu: id, nazwa, miasto, kod pocztowy, ulica
     */
    public function jsonSerialize()
    {
        return
            [
                'id' => $this->getId(),
                'storeName' => $this->getStoreName(),
                'city' => $this->getCity(),
                'postCode' => $this->getPostCode(),
                'street' => $this->getStreet()
            ];
    }

}