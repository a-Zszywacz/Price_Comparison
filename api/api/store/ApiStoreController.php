<?php


namespace Api;
include_once "DataBaseConnection.php";
include_once "../objects/ConreteStore.php";
include_once "../objects/Store.php";
include_once "../objects/Product.php";

use PDO;
use PriceComparison\ConcreteStore;
use PriceComparison\Product;
use PriceComparison\ProductInStore;
use PriceComparison\Store;


/**
 * Kontroler zwracający dane sklepu, na podstawie parametru.
 * @package Api
 */
class ApiStoreController
{
    /**
     * \brief Zwraca wszystkie sklepy z bazy danych.
     * Zwraca wszystkie sklepy z bazy danych.
     * @return array dane sklepu: id, nazwa, miasto, kod pocztowy, ulica.
     * @throws \Exception wyjątek
     */
    public function getAllStores()
    {
        $query = "SELECT idConreteStore, storeName, city, postCode, street FROM Store 
            JOIN ConreteStore ON idShop = idStore_fk JOIN StoreLocalization ON idStoreLocalization = idStoreLocalization_fk;";

        $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
        $stmt->execute();

        $stores = [];
        if($stmt->rowCount())
        {
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                extract($row);
                array_push($stores, new ConcreteStore($idConreteStore, $storeName, $city, $postCode, $street));
            }
        }
        return $stores;
    }

    /**
     * \brief Zwraca dane lokalizacyjne żądanego konkretnego sklepu.
     * Zwraca dane lokalizacyjne żądanego konkretnego sklepu.
     * @param null $id ID
     * @param null $name nazwa
     * @param null $_postCode kod pocztowy
     * @param null $_street ulica
     * @param null $_city miasto
     * @return ConcreteStore dane sklepu: id, nazwa, miasto, kod pocztowy, ulica
     * @throws \Exception wyjątek
     */
    public function getStore($id=null, $name=null, $_postCode = null, $_street = null, $_city = null)
    {
        if($id != null){
            settype($id, "int");
        $query = "SELECT idConreteStore, storeName, city, postCode, street FROM Store 
            JOIN ConreteStore ON idShop = idStore_fk JOIN StoreLocalization ON idStoreLocalization = idStoreLocalization_fk
            WHERE idConreteStore = ".$id.";";

            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->execute();

            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new ConcreteStore($idConreteStore, $storeName, $city, $postCode, $street);
            }
        }

        if($name != null && $_postCode != null && $_street != null && $_city != null){

            $query = "SELECT idConreteStore, storeName, city, postCode, street FROM Store 
            JOIN ConreteStore ON idShop = idStore_fk JOIN StoreLocalization ON idStoreLocalization = idStoreLocalization_fk
            WHERE storeName = :store_name AND postCode = :post_code AND street LIKE :street AND city = :city";
            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);

            $stmt->bindValue(':store_name',$name);
            $stmt->bindValue(':post_code',$_postCode);
            $stmt->bindValue(':street','%'.$_street);
            $stmt->bindValue(':city',$_city);

            $stmt->execute();
            if($stmt->rowCount())
            {
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                extract($row);

                return new ConcreteStore($idConreteStore, $storeName, $city, $postCode, $street);
            }
        }
    }

    /**
     * \brief Zwraca sklepy, na podstawie produktu, który jest w nich dostępny.
     * Zwraca sklepy, na podstawie produktu, który jest w nich dostępny.
     * @param Product|null $product konkretny produkt
     * @param null $id id id produktu
     * @param null $producerCode kod producenta
     * @return array dane sklepu: id, nazwa, miasto, kod pocztowy, lokalizacja
     * @throws \Exception wyjątek
     */
    public function getStoresByProduct(Product $product = null, $id = null, $producerCode = null)
    {

        if( $producerCode == null )
        {
            $id = $product ? $product->getId(): $id;
            settype($id, "int");


            $query = "SELECT ConreteStore.idConreteStore, Store.storeName, StoreLocalization.city, StoreLocalization.postCode, StoreLocalization.street 
                FROM ConreteStore 
                JOIN ProductInStore ON ConreteStore.idConreteStore = ProductInStore.idConreteStore_fk 
                JOIN Store ON Store.idShop = ConreteStore.idStore_fk 
                JOIN StoreLocalization ON StoreLocalization.idStoreLocalization = ConreteStore.idStoreLocalization_fk 
                WHERE ProductInStore.idProduct_fk = :id;";
        }
        else
        {
            $id = $producerCode;

            settype($id, "int");

            $query = "SELECT ConreteStore.idConreteStore, Store.storeName, StoreLocalization.city, StoreLocalization.postCode, StoreLocalization.street 
                FROM ConreteStore 
                JOIN ProductInStore ON ConreteStore.idConreteStore = ProductInStore.idConreteStore_fk 
                JOIN Store ON Store.idShop = ConreteStore.idStore_fk 
                JOIN StoreLocalization ON StoreLocalization.idStoreLocalization = ConreteStore.idStoreLocalization_fk 
                JOIN Products ON Products.idProduct = ProductInStore.idProduct_fk
                WHERE Products.producerCode = :id;";
        }

            $stmt = DataBaseConnection::getDBConnectionInstance()->prepare($query);
            $stmt->bindValue(':id',$id);

            $stmt->execute();
            $stores =[];
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
                extract($row);
                array_push($stores, new ConcreteStore($idConreteStore, $storeName, $city, $postCode, $street));
            }
            return $stores;
    }



}
