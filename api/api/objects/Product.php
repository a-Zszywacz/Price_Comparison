<?php


namespace PriceComparison;


use mysql_xdevapi\Exception;

/**
 * Klasa służąca do zarządzania produktami w bazie danych.
 * @package PriceComparison
 */
class Product implements \JsonSerializable
{
    private $id; /**< \brief ID Produktu */
    private $productName; /**< \brief Nazwa produktu */
    private $producerCode; /**< \brief Kod producenta */
    private $imageUrl; /**< \brief Zdjęcie */

    /**
     * \brief Konstruktor parametryczny tworzący produkt na podstawie ID, nazwy, kodu producenta.
     * Konstruktor parametryczny tworzący produkt na podstawie ID, nazwy, kodu producenta.
     * @param null $id ID
     * @param $productName Nazwa produktu
     * @param $producerCode Kod producenta
     * @param null $imageUrl Zdjęcie
     */
    public function __construct($id = null, $productName, $producerCode, $imageUrl = null)
    {
        settype($id, "int");
        $this->id = $id;

        if(!is_string($productName) || !is_string($producerCode)) throw new Exception("all types must be strings!");

        $this->productName = $productName;
        $this->producerCode = $producerCode;
        $this->imageUrl = $imageUrl;
    }


    /**
     * \brief Funkcja typu GET. Zwraca ID Produktu.
     * Funkcja typu GET. Zwraca ID Produktu.
     * @return string ID
     */
    public function getId() :string
    {
        return $this->id;
    }


    /**
     * \brief Funkcja typu GET. Zwraca kod producenta produktu.
     * Funkcja typu GET. Zwraca kod producenta produktu.
     * @return string kod producenta produktu
     */
    public function getProducerCode() :string
    {
        return $this->producerCode;
    }


    /**
     * \brief Funkcja typu GET. Zwraca Nazwe Produktu.
     * Funkcja typu GET. Zwraca Nazwe Produktu.
     * @return string nazwa produktu
     */
    public function getProductName() :string
    {
        return $this->productName;
    }


    /**
     * \brief Funkcja typu SET. Ustawia ID produktu.
     * Funkcja typu SET. Ustawia ID produktu.
     * @param $id ID
     */
    public function setId($id): void
    {
        $this->id = $id;
    }


    /**
     * \brief Metoda typu SET. Ustawia kod producenta produktu.
     * Metoda typu SET. Ustawia kod producenta produktu.
     * @param $producerCode kod producenta produktu
     */
    public function setProducerCode($producerCode): void
    {
        $this->producerCode = $producerCode;
    }

    /**
     * \brief Metoda typu SET. Ustawia nazwe producenta produktu.
     * Metoda typu SET. Ustawia nazwe producenta produktu.
     * @param $productName nazwa producenta produktu
     */
    public function setProductName($productName): void
    {
        $this->productName = $productName;
    }

    /**
     * \brief Funkcja przekazuje dane do pliku JSON.
     * Funkcja przekazuje dane do pliku JSON.
     * @return array|mixed tablica z danymi
     */
    public function jsonSerialize()
    {
        return [
            'id' => $this->getId(),
            'productName' => $this->getProductName(),
            'producerCode' => $this->getProducerCode(),
            'imageURL' => $this->getImageUrl()
        ];
    }

    /**
     * \brief Funkcja typu GET. Zwraca URL obrazka.
     * Funkcja typu GET. Zwraca URL obrazka.
     * @return null|string url obrazka
     */
    public function getImageUrl(): ?string
    {
        return $this->imageUrl;
    }

    /**
     * \brief Metoda typu SET. Ustawia adres URL obrazka.
     * Metoda typu SET. Ustawia adres URL obrazka.
     * @param null $imageUrl
     */
    public function setImageUrl($imageUrl): void
    {
        $this->imageUrl = $imageUrl;
    }
}