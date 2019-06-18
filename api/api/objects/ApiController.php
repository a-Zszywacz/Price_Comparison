<?php


namespace PriceComparison;

/**
 * \brief Kontroler API. Pobiera obiekty z bazy danych.
 * Kontroler API. Pobiera obiekty z bazy danych.
 * Interface ApiController
 * @package PriceComparison
 */
interface ApiController
{
    /**
     *  Funkcja pobiera wszystkie obiekty.
     */
    public function getAllObjects();

    /**
     * Funkcja pobiera obiekt o danym ID.
     * @param null $id ID
     */
    public function getObject($id = null);

}