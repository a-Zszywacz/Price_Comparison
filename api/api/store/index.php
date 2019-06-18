<?php

use PriceComparison\Product;
use PriceComparison\ConcreteStore;

include_once "../objects/ConreteStore.php";
include_once "ApiController.php";

function is_multi_array( $arr ) {
    rsort( $arr );
    return isset( $arr[0] ) && is_array( $arr[0] );
}


error_reporting(E_ALL);
ini_set('display_errors', '1');
// Show all errors.
error_reporting(E_ALL);

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Accept-language: en\r\n");


$apiController = new ApiController();
$jsonToEncode = null;

if (isset($_GET['fullInformations'])) {
    $jsonToEncode = $apiController->getCompleteInformation((int)$_GET['fullInformations']);
}


// database connection will be here
if(isset($_GET['What']))
{


    if($_GET['What'] == 'Store'){
        $apiController = ApiController::getProductController(ApiController::STORE);

        if(isset($_GET['StoreByID'])){
            $jsonToEncode = $apiController->getStore($_GET['StoreByID']);
        }

        if(isset($_GET['GetAllStores'])){
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getAllStores();
        }

        if( isset($_GET['StoreName']) &&
            isset($_GET['PostCode']) &&
            isset($_GET['Street']) &&
            isset($_GET['City'])){
            $jsonToEncode = $apiController->getStore(null, $_GET['StoreName'], $_GET['PostCode'],$_GET['Street'], $_GET['City']);
        }
        if( isset($_GET['StoreByProductName']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getStoresByProduct(new Product(null, $_GET['StoreByProductName'],null));
        }
        if(isset($_GET['StoreByProducerCode']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getStoresByProduct(null, null, $_GET['StoreByProducerCode']);
        }
        if(isset($_GET['StoreByProductID']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getStoresByProduct(null, $_GET['StoreByProductID']);
        }
    }

    if($_GET['What'] == 'Products'){
        $apiController = ApiController::getProductController(ApiController::PRODUCTS);

        if(isset($_GET['ProductByID']))
        {
            $jsonToEncode = $apiController->getProduct($_GET['ProductByID'],null,null);
        }
        if(isset($_GET['ProductByProducerCode']))
        {
            $jsonToEncode = $apiController->getProduct(null,null,$_GET['ProductByProducerCode']);
        }
        if(isset($_GET['ProductByName']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getProduct(null,$_GET['ProductByName'],null);
        }
        if(isset($_GET['GetAllProducts']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getAllProducts();
        }
        if(isset($_GET['GetProductsByStoreID']))
        {
            $jsonToEncode = array();
            $jsonToEncode = $apiController->getProductsByStore(null,$_GET['GetProductsByStoreID']);
        }
        if(isset($_GET['GetProductInStoreByID']))
        {
            $jsonToEncode = $apiController->getProductInStore($_GET['GetProductInStoreByID']);
        }
        //if(isset($_GET["GetProductsInStoreByProducerCode"]))
    }
}

if($jsonToEncode == null || empty($jsonToEncode)){
    http_response_code(404);
    die();
}
else if(is_object($jsonToEncode) && !is_array($jsonToEncode)){
    echo json_encode($jsonToEncode);
    http_response_code(200);
    die();
}
else if(is_array($jsonToEncode))
{
    $all_data = array();
    foreach($jsonToEncode as $obj){
        $all_data[] = $obj->jsonSerialize();
    }
    echo json_encode($all_data);
    http_response_code(200);
    die();
}

