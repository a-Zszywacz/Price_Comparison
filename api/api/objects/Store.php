<?php


namespace PriceComparison;

/**
 * Klasa służąca do zarządzania sklepami w bazie danych.
 * @package PriceComparison
 */
class Store implements \JsonSerializable
{
    private $id; /**< \brief ID sklepu */
    private $storeName; /**< \brief Nazwa sklepu */


    /**
     * \brief Konstruktor parametryczny, tworzący sklep na podstawie ID, oraz nazwy.
     * Konstruktor parametryczny, tworzący sklep na podstawie ID, oraz nazwy.
     * @param null $id ID sklepu
     * @param null $storeName nazwa sklepu
     */
    public function __construct($id = null, $storeName = null){
        $this->id = $id;
        $this->storeName = $storeName;
    }

    /**
     * \brief Funkcja typu GET. Zwraca ID sklepu.
     * Funkcja typu GET. Zwraca ID sklepu.
     * @return mixed ID sklepu
     */
    public function getId()
    {
        return $this->id;
    }


    /**
     * \brief Funkcja typu GET. Zwraca nazwe sklepu.
     * Funkcja typu GET. Zwraca nazwe sklepu.
     * @return mixed nazwa sklepu
     */
    public function getStoreName()
    {
        return $this->storeName;
    }


    /**
     * \brief Metoda typu SET. Ustawia ID sklepu.
     * Metoda typu SET. Ustawia ID sklepu.
     * @param mixed $id ID sklepu
     */
    public function setId($id): void
    {
        $this->id = $id;
    }

    /**
     * /brief Metoda typu SET. Ustawia nazwe sklepu.
     * Metoda typu SET. Ustawia nazwe sklepu.
     * @param mixed $storeName nazwa sklepu
     */
    public function setStoreName($storeName): void
    {
        $this->storeName = $storeName;
    }

    /**
     * \brief Funkcja przekazuje dane do pliku JSON.
     * Funkcja przekazuje dane do pliku JSON.
     * @return array|mixed dane produktu: Id, nazwa sklepu
     */
    public function jsonSerialize()
    {
        return [
            'id' => $this->getId(),
            'storeName' => $this->getStoreName()
        ];
    }

}