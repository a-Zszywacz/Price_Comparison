<?php

namespace Api;
use Exception;
use PDO;
use PDOException;

class DataBaseConnection
{
    private $host; /**< \brief host bazy danych */
    private $dbName; /**< \brief nazwa bazy danych */
    private $dbUser; /**< \brief użytkownik */
    private $dbPassword; /**< \brief hasło */

    private $connection; /**< \brief połączenie*/

    private static $connectionInstance; /**< \brief instancja połączenia*/
    /**
     * Konstruktor parametryczny. Tworzy połączenie z bazą danych, na podstawie podanych danych.
     * @param $host host bazy danych
     * @param $dbName nazwa bazy danych
     * @param $dbUser nazwa użytkownika
     * @param $dbPassword hasło
     * @throws Exception wyjątek
     */
    private function __construct($host, $dbName, $dbUser, $dbPassword)
    {
        if(!is_string($host) || !is_string($dbName) || !is_string($dbUser) || !is_string($dbPassword)){
            throw new Exception('All arguments must be type string!');
        }
        $this->host = $host;
        $this->dbName = $dbName;
        $this->dbUser = $dbUser;
        $this->dbPassword = $dbPassword;

        try{
        $this->connection = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->dbName, $this->dbUser, $this->dbPassword);
        $this->connection->exec("set names utf8");
        }catch (PDOException $exception)
        {
            echo "Connection error: " . $exception->getMessage();
        }
    }

    /**
     * \brief Funkcja inicjalizująca połączenie z bazą danych, jeśli nie jest już zrealizowane.
     * Funkcja inicjalizująca połączenie z bazą danych, jeśli nie jest już zrealizowane.
     * @param $host host bazy danych
     * @param $dbName nazwa bazy danych
     * @param $dbUser nazwa użytkownika
     * @param $dbPassword hasło
     * @throws Exception wyjątek
     */
    static function initDBConnectionInstance($host, $dbName, $dbUser, $dbPassword) : void
    {
        if(empty($connectionInstance)){
            self::$connectionInstance = new DataBaseConnection($host, $dbName, $dbUser, $dbPassword);
        }
        else{
            throw new Exception('there can be only one connection to the db');
        }
    }

    /**
     * \brief Zwraca instancje połączenia bazy danych.
     * Zwraca instancje połączenia bazy danych.
     * @return PDO połączenie
     * @throws Exception wyjątek
     */
    static function getDBConnectionInstance() : PDO
    {
        if(!empty(self::$connectionInstance) && self::$connectionInstance instanceof self)
        {
            return self::$connectionInstance->connection;
        }
        else{
            throw new Exception('first you have to initialize the method "initDBConnectionInstance"');
        }
    }

    /**
     * \brief Zamyka połączenie z bażą danych.
     * Zamyka połączenie z bażą danych.
     */
    static function destroyDBConnectionInstance(): void
    {
        self::$connectionInstance->connection = null;
    }





}