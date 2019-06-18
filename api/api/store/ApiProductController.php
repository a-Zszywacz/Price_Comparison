<?php


namespace Api;

include_once "../objects/ProductInStore.php";

use PDO;
use PriceComparison\ProductInStore;
use PriceComparison\Store;
use PriceComparison\ConcreteStore;
use PriceComparison\Product;

/**
 * Kontroler zwracający dane produktu, na podstawie parametru.
 * @package Api
 */
class ApiProductController
{
    /**
     * \brief Zwraca wszystkie dostępne produkty z bazy danych.
     * Zwraca wszystkie dostępne produkty z bazy danych.
     * @return array dane produktów: id, nazwa, kod producenta, adres obrazka.
     * @throws \Exception wyjątek
     */
    public function getAllProducts()
    {
        $query = " SELECT Products.idProduct, Products.productName, Products.producerCode, Products.imageURL FROM Products";
        $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
        $stmt->execute();
        $products = [];
        if($stmt->rowCount())
        {
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                extract($row);
                array_push($products, new Product($row['idProduct'], $row['productName'], $row['producerCode'], $row['imageURL']));
            }
        }
        return $products;
    }

    /**
     * \brief Zwraca informacje na temat żądanego produktu.
     * Zwraca informacje na temat żądanego produktu.
     * @param null $id ID produktu
     * @param null $name nazwa produktu
     * @param null $producerID ID producenta
     * @return array|Product dane produktu: ID, nazwa, ID producenta, adres obrazka.
     * @throws \Exception wyjątek
     */
    public function getProduct($id = null, $name = null, $producerID = null)
    {
        if($id != null && $name == null && $producerID == null){
            settype($id, "int");
            $query = "SELECT Products.idProduct, Products.productName, Products.producerCode FROM `Products` WHERE Products.idProduct =  ".$id.";";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->execute();

            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new Product($row['id'], $row['productName'], $row['producerCode']);
            }
        }
        if($name != null && $producerID == null){
            $query = "SELECT Products.idProduct, Products.productName, Products.producerCode, Products.imageURL FROM Products
            WHERE Products.productName LIKE :product_name;";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);

            $stmt->bindValue(':product_name',"%".$name."%");

            $stmt->execute();

            $products = [];
            if($stmt->rowCount())
            {
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                    extract($row);
                    array_push($products, new Product($row['idProduct'], $row['productName'], $row['producerCode'], $row['imageURL']));
                }
            }
            return $products;
        }
        if($producerID != null){
            $query = "SELECT Products.idProduct, Products.productName, Products.producerCode FROM `Products` WHERE Products.producerCode = :producerID;";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->bindValue(':producerID',$producerID);
            $stmt->execute();
            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new Product($row['idProduct'], $row['productName'], $row['producerCode'], $row['imageURL']);
            }
        }
    }

    /**
     * \brief Zwraca dane produktu w danym sklepie.
     * Zwraca dane produktu w danym sklepie.
     * @param null $id ID produktu
     * @param null $name nazwa produktu
     * @param null $producerID kod producenta
     * @return array|ProductInStore dane produkttu: id produktu, nazwa, kod producenta, adres obrazka, waluta, cena, czas trwania promocji
     * @throws \Exception wyjątek
     */
    public function getProductInStore($id = null, $name = null, $producerID = null)
    {
        if($id != null && $name == null && $producerID == null){
            settype($id, "int");
            $query = "SELECT ProductInStore.idProductInStore, Products.productName, 
                            Products.producerCode, Products.imageURL, ProductInStore.currency, ProductInStore.price, 
                            ProductInStore.durationOfPromotionFrom, ProductInStore.durationOfPromotionTo 
                            FROM `ProductInStore` JOIN Products ON Products.idProduct = ProductInStore.idProduct_fk WHERE ProductInStore.idProductInStore = ".$id.";";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->execute();

            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new ProductInStore(
                    $row['idProductInStore'],
                    $row['productName'],
                    $row['producerCode'],
                    $row['imageURL'],
                    $row['currency'],
                    $row['price'],
                    $row['durationOfPromotionFrom'],
                    $row['durationOfPromotionTo']
                );
            }
        }
        if($name != null && $producerID == null){
            $query = "SELECT ProductInStore.idProductInStore, Products.productName, 
                            Products.producerCode, Products.imageURL, ProductInStore.currency, ProductInStore.price, 
                            ProductInStore.durationOfPromotionFrom, ProductInStore.durationOfPromotionTo 
                            FROM `ProductInStore` JOIN Products on Products.idProduct = ProductInStore.idProduct_fk WHERE ProductInStore.productName LIKE :product_name;";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);

            $stmt->bindValue(':product_name',"%".$name."%");

            $stmt->execute();

            $products = [];
            if($stmt->rowCount())
            {
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                    extract($row);
                    array_push($products, new ProductInStore(
                        $row['idProductInStore'],
                        $row['productName'],
                        $row['producerCode'],
                        $row['imageURL'],
                        $row['currency'],
                        $row['price'],
                        $row['durationOfPromotionFrom'],
                        $row['durationOfPromotionTo']
                    ));
                }
            }
            return $products;
        }
        if($producerID != null){
            $query = "SELECT ProductInStore.idProductInStore, Products.productName, 
                            Products.producerCode, ProductInStore.currency, Products.imageURL, ProductInStore.price, 
                            ProductInStore.durationOfPromotionFrom, ProductInStore.durationOfPromotionTo 
                            FROM `ProductInStore` JOIN Products on Products.idProduct = ProductInStore.idProduct_fk WHERE ProductInStore.producerCode = :producerID;";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->bindValue(':producerID',$producerID);
            $stmt->execute();
            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new ProductInStore(
                    $row['idProductInStore'],
                    $row['productName'],
                    $row['producerCode'],
                    $row['imageURL'],
                    $row['currency'],
                    $row['price'],
                    $row['durationOfPromotionFrom'],
                    $row['durationOfPromotionTo']
                );
            }
        }
    }

    /**
     * \brief Zwraca dane produktu na podstawie sklepu.
     * Zwraca dane produktu na podstawie sklepu.
     * @param Store|null $store sklep
     * @param null $id ID sklepu
     * @return array dane produktu: id produktu, nazwa, kod producenta, adres obrazka, waluta, cena, czas trwania promocji
     * @throws \Exception wyjątek
     */
    public function getProductsByStore(Store $store = null, $id = null)
    {
        $id = $store ? $store->getId() : $id;
        settype($id, "int");

        $query = "SELECT ProductInStore.idProductInStore, Products.productName, Products.producerCode, Products.imageURL, ProductInStore.currency, ProductInStore.price, ProductInStore.durationOfPromotionFrom, ProductInStore.durationOfPromotionTo 
                FROM ProductInStore 
                JOIN Products ON ProductInStore.idProduct_fk = Products.idProduct 
                WHERE ProductInStore.idConreteStore_fk = :id;";

        $stmt = \Api\DataBaseConnection::getDBConnectionInstance()->prepare($query);
        $stmt->bindValue(':id',$id);

        $stmt->execute();

        $products = [];
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            array_push($products, new ProductInStore(
                $row['idProductInStore'],
                $row['productName'],
                $row['producerCode'],
                $row['imageURL'],
                $row['currency'],
                $row['price'],
                $row['durationOfPromotionFrom'],
                $row['durationOfPromotionTo']
            ));
        }
        return $products;
    }


}