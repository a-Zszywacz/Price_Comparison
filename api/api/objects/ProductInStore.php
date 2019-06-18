<?php


namespace PriceComparison;

/**
 * Klasa służąca do zarządzania produktami w konkretnym sklepie.
 * @package PriceComparison
 */
class ProductInStore extends Product implements \JsonSerializable
{
    private $price; /**< \brief Cena produktu */
    private $currency; /**< \brief Waluta */
    private $promotionFrom; /**< \brief Czas trwania promocji OD */
    private $promotionTo; /**< \brief Czas trwania promocji DO */

    private $store; /**< \brief Sklep */

    /**
     * \brief Konstruktor parametryczny tworzący produkt w sklepie na podstawie ID, nazwy, kodu, ilości, ceny, czasu promocji.
     * Konstruktor parametryczny tworzący produkt w sklepie na podstawie ID, nazwy, kodu, ilości, ceny, czasu promocji.
     * @param null $id ID
     * @param $productName nazwa produktu
     * @param $producerCode kod producenta
     * @param null $imageUrl adres obrazka
     * @param $price cena produktu
     * @param $currency waluta
     * @param $promotionFrom czas trwania promocji od
     * @param $promotionTo czas trwania promocji do
     */
    public function __construct($id = null, $productName, $producerCode, $imageUrl = null, $price, $currency, $promotionFrom, $promotionTo)
    {
        parent::__construct($id, $productName, $producerCode, $imageUrl);

        $this->price = $price;
        $this->currency = $currency;
        $this->promotionFrom = $promotionFrom;
        $this->promotionTo = $promotionTo;
    }

    /**
     * \brief Funkcja typu GET. Zwraca walute.
     * Funkcja typu GET. Zwraca walute.
     * @return mixed waluta
     */
    public function getCurrency()
    {
        return $this->currency;
    }


    /**
     * \brief Funkcja typu GET. Zwraca czas zakończenia promocji.
     * Funkcja typu GET. Zwraca czas zakończenia promocji.
     * @return mixed czas zakończenia promocji
     */
    public function getPromotionTo()
    {
        return $this->promotionTo;
    }

    /**
     * \brief Funkcja typu GET. Zwraca czas rozpoczęcia promocji.
     * Funkcja typu GET. Zwraca czas rozpoczęcia promocji.
     * @return mixed czas rozpoczęcia promocji
     */
    public function getPromotionFrom()
    {
        return $this->promotionFrom;
    }

    /**
     * \brief Funckja typu GET. Zwraca cene produktu.
     * Funckja typu GET. Zwraca cene produktu.
     * @return mixed cena produktu
     */
    public function getPrice()
    {
        return $this->price;
    }

    /**
     * \brief Funkcja typu SET. Ustawia walute.
     * Funkcja typu SET. Zwraca walute.
     * @param mixed $currency waluta
     */
    public function setCurrency($currency): void
    {
        $this->currency = $currency;
    }

    /**
     * \brief Funkcja typu SET.  Ustawia cene produktu.
     * Funkcja typu SET.  Ustawia cene produktu.
     * @param mixed $price cena produktu
     */
    public function setPrice($price): void
    {
        $this->price = $price;
    }

    /**
     * \brief Funkcja typu SET. Ustawia czas rozpoczęcia promocji.
     * Funkcja typu SET. Ustawia czas rozpoczęcia promocji.
     * @param mixed $promotionFrom czas rozpoczęcia promocji
     */
    public function setPromotionFrom($promotionFrom): void
    {
        $this->promotionFrom = $promotionFrom;
    }

    /**
     * /brief Funkcja typu SET. Ustawia czas zakończenia promocji.
     * Funkcja typu SET. Ustawia czas zakończenia promocji.
     * @param mixed $promotionTo czas zakończenia promocji
     */
    public function setPromotionTo($promotionTo): void
    {
        $this->promotionTo = $promotionTo;
    }

    /**
     * \brief Dodaje sklepy do listy sklepów.
     * Dodaje sklepy do listy sklepów.
     * @param ConcreteStore $store sklep
     */
    public function addStore(ConcreteStore $store)
    {
        $this->store = $store;
    }

    /**
     * \brief Funkcja przekazuje dane do pliku JSON.
     * Funkcja przekazuje dane do pliku JSON.
     * @return array|mixed dane produktu: id, nazwa produktu, kod producenta, url obrazka, cena, waluta, czas trwania promocji od, czas trwania promocji do
     */
    public function jsonSerialize()
    {
        if (! empty($this->store)) {
            return [
                'id' => $this->getId(),
                'productName' => $this->getProductName(),
                'producerCode' => $this->getProducerCode(),
                'imageURL' => $this->getImageUrl(),
                'price' => $this->getPrice(),
                'currency' => $this->getCurrency(),
                'promotionFrom' => $this->getPromotionFrom(),
                'promotionTo' => $this->getPromotionTo(),
                'store' => $this->store->jsonSerialize()
            ];
        }
        return [
            'id' => $this->getId(),
            'productName' => $this->getProductName(),
            'producerCode' => $this->getProducerCode(),
            'price' => $this->getPrice(),
            'currency' => $this->getCurrency(),
            'promotionFrom' => $this->getPromotionFrom(),
            'promotionTo' => $this->getPromotionTo()
        ];
    }

}