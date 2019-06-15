package com.example.price_comparison.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

/**
 * Klasa zaimplemmentowana jako kontroler bazy danych
 */
public class ApiController {



    String saldo = ""; /**< ilość środków na koncie */

    private String productUrl = "http://tymejczyk.home.pl/api_/store/index.php?What=Products"; /**< Adres produktu */
    private String storeUrl =   "http://tymejczyk.home.pl/api_/store/index.php?What=Store"; /**< Adres sklepu */


    /**
     * Klasa służąca do pobierania danych z bazy danych w postaci JSON.
     */
    public class GetDataSync extends AsyncTask<Void, Void, Void> {


 @Override
        protected Void doInBackground(Void... params) {
            try {
                getData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void getData() throws IOException, JSONException {

    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Metoda służąca do odczytu JSON z adresu URL.
     * @param url adres URL
     * @return JSON
     * @throws IOException
     * @throws JSONException
     */
    public JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * Metoda służąca do odczytu obiektu JSON z adresu URL.
     * @param url adres URL
     * @return JSON
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     * /brief Metoda typu GET. Zwraca listę wszystkich produktów.
     * Metoda typu GET. Zwraca listę wszystkich produktów. Zwracane pola: Id, Nazwa, Kod producenta.
     * @return lista wszystkich produktów
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<Product> getAllProducts() throws IOException, JSONException {
        JSONArray json = this.readJsonFromUrl(this.productUrl+"&GetAllProducts");

        ArrayList<Product> products = new ArrayList<Product>();
        for(int i =0; i < json.length(); i++)
        {
            JSONObject jsonObj = (JSONObject) json.get(i);
            Product p;
            p = new Product(jsonObj.getInt("id"), jsonObj.getString("productName"), jsonObj.getString("producerCode"));
            products.add(p);
        }
        return products;
    }

    /**
     * /brief Metoda typu GET. Zwraca listę wszystkich sklepów.
     * Metoda typu GET. Zwraca listę wszystkich sklepów. Zwracane pola: Id, nazwa sklepu, miasto, kod pocztowy, ulica.
     * @return lista wszystkich sklepów
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<ConcreteStore> getAllStores() throws IOException, JSONException {
        JSONArray json = this.readJsonFromUrl(this.storeUrl+"&GetAllStores");

        ArrayList<ConcreteStore> stores = new ArrayList<ConcreteStore>();
        for(int i =0; i < json.length(); i++)
        {
            JSONObject jsonObj = (JSONObject) json.get(i);
            ConcreteStore store;
            store = new ConcreteStore(jsonObj.getInt("id"),
                    jsonObj.getString("storeName"),
                    jsonObj.getString("city"),
                    jsonObj.getString("postCode"),
                    jsonObj.getString("street")
                    );
            stores.add(store);
        }
        return stores;
    }

    /**
     * /brief Metoda zwraca produkt na podstawie kodu producenta.
     * Metoda zwraca produkt na podstawie kodu producenta. Zwracane pola: Id, nazwa, kod producenta
     * @param producerCode Kod producenta
     * @return Produkt
     * @throws IOException
     * @throws JSONException
     */
    public  Product getProductByProducerCode(String producerCode) throws IOException, JSONException {
        JSONObject jsonObj = this.readJsonObjectFromUrl(this.productUrl+"&ProductByProducerCode="+producerCode);
        Product p = null;
        p = new Product(jsonObj.getInt("id"), jsonObj.getString("productName"), jsonObj.getString("producerCode"));
        return p;
    }

    /**
     * /brief Metoda na podstawie kodu producenta zwraca sklep, w którym znajduje się produkt.
     * Metoda na podstawie kodu producenta zwraca sklep, w którym znajduje się produkt. Zwracane pola: id, nazwa sklepu, miasto, kod pocztowy, ulica.
     * @param producerCode
     * @return Sklep
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<ConcreteStore> getStoresByProducerCode(String producerCode) throws IOException, JSONException {
        JSONArray json = this.readJsonFromUrl(this.storeUrl+"&StoreByProducerCode="+producerCode);

        ArrayList<ConcreteStore> stores = new ArrayList<ConcreteStore>();
        for(int i =0; i < json.length(); i++)
        {
            JSONObject jsonObj = (JSONObject) json.get(i);
            ConcreteStore store;
            if(jsonObj.getString("storeName") != null) {
                store = new ConcreteStore(jsonObj.getInt("id"),
                        jsonObj.getString("storeName"),
                        jsonObj.getString("city"),
                        jsonObj.getString("postCode"),
                        jsonObj.getString("street")
                );

                stores.add(store);
            }
        }
        return stores;
    }

    /**
     * /brief Metoda zwraca produkt w danym sklepie, na podstawie ID sklepu.
     * Metoda zwraca produkt w danym sklepie, na podstawie ID sklepu. Zwracane pola: Id, nazwa produktu, kod producenta, waluta, cena, data promocji od, data promocji do.
     * @param id
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<ProductInStore> getConreteProductsByStoreID(int id) throws IOException, JSONException {
        JSONArray json = this.readJsonFromUrl(this.productUrl+"&GetProductsByStoreID="+Integer.toString(id));

        ArrayList<ProductInStore> products = new ArrayList<ProductInStore>();
        for(int i =0; i < json.length(); i++)
        {
            JSONObject jsonObj = (JSONObject) json.get(i);
            ProductInStore product;
            if(jsonObj.getString("productName") != null) {
                product = new ProductInStore(
                        jsonObj.getInt("id"),
                        jsonObj.getString("productName"),
                        jsonObj.getString("producerCode"),
                        jsonObj.getString("currency"),
                        Float.parseFloat(jsonObj.getString("price")),
                        jsonObj.getString("promotionFrom"),
                        jsonObj.getString("promotionTo")
                );

                products.add(product);
            }
        }
        return products;
    }
}