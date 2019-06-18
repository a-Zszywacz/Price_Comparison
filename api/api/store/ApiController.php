<?php

include_once "DataBaseConnection.php";
include_once "ApiStoreController.php";
include_once "ApiProductController.php";

use Api\DataBaseConnection;
use Api\ApiProductController;
use Api\ApiStoreController;
use PriceComparison\ConcreteStore;
use PriceComparison\Product;
use PriceComparison\ProductInStore;

/**
 * Klasa zaimplemmentowana jako kontroler bazy danych.
 */
class ApiController
{

    const STORE = 1; /**< \brief stała pomocnicza: sklep */
    const PRODUCTS = 2; /**< \brief stała pomocnicza: produkty */

    private static $isRun; /**< \brief czy jest uruchomiony */

    /**
     * \brief Konstruktor parametryczny, tworzący połączenie z bazą danych.
     * Konstruktor parametryczny, tworzący połączenie z bazą danych.
     * @throws Exception wyjątek
     */
    public function __construct()
    {
        if(!self::$isRun){
            DataBaseConnection::initDBConnectionInstance(
                "127.0.0.1",
               // "20766017_mob_app",
               // "20766017_mob_app",

                //"RyA&G*n:Y,D:"
                //
                "PriceComparison",
                "tymejczyk",
                "mateusz12"
            );

            self::$isRun = true;
        }
    }

    /**
     * Zwraca odpowiedni kontroler, w zależności od tego, co jest wymagane.
     * @param $what jaki kontroler wybrać
     * @return ApiProductController|ApiStoreController zwracany kontroler (kontroler sklepu, lub produktu)
     */
    static function getProductController($what)
    {
        if($what == self::STORE) return new ApiStoreController();
        if($what == self::PRODUCTS) return new ApiProductController();
    }

    /**
     * Zwraca kompletną informacje na temat sklepów, oraz produktów.
     * @param int $producerCode kod produktu kod producenta
     * @return array dane produktów: id produktu, nazwa, kod producenta, adres obrazka, waluta, cena, czas promocji, id sklepu, nazwa sklepu, lokalizacja, kod pocztowy, , ulica.
     * @throws Exception wyjątek
     */
    public function getCompleteInformation(int $producerCode)
    {
        $query = "
            SELECT * FROM `Products` 
            JOIN ProductInStore ON ProductInStore.idProduct_fk = Products.idProduct 
            JOIN ConreteStore ON ConreteStore.idConreteStore = ProductInStore.idConreteStore_fk 
            JOIN Store ON ConreteStore.idStore_fk = Store.idShop 
            JOIN StoreLocalization ON StoreLocalization.idStoreLocalization = ConreteStore.idStoreLocalization_fk  
            WHERE Products.producerCode = :producerCode
        ";

        $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
        $stmt->bindParam(':producerCode', $producerCode);
        $stmt->execute();
        $products = [];
        if($stmt->rowCount())
        {
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                extract($row);
                    $product = new ProductInStore(
                        $row['idProductInStore'],
                        $row['productName'],
                        $row['producerCode'],
                        $row['imageURL'],
                        $row['currency'],
                        $row['price'],
                        $row['durationOfPromotionFrom'],
                        $row['durationOfPromotionTo']
                    );

                $product->addStore(new ConcreteStore(
                    $row['idConreteStore'],
                    $row['storeName'],
                    $row['idStoreLocalization'],
                    $row['postCode'],
                    $row['street']
                ));

                array_push($products, $product);
            }
        }
        return $products;
    }
}