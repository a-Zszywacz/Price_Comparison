package com.example.price_comparison;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.Product;
import com.example.price_comparison.customlist.SingleStore;
import com.example.price_comparison.customlist.StoreList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasa służąca do zarządzania zeskanowanym prouktem. Rozszerza klasę AppCompatActivity. Implementuje GoogleApiClient.
 */
public class ScannedProduct extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    ApiController api = new ApiController(); /**< \brief Obiekt kontrolera bazy danych OX*/

    ArrayList<SingleStore> dataList;  /**< \brief Lista przechowująca dane */
    ListView listView; /**< \brief Obiekt klasy ListView */
    private static StoreList storeList; /**< \brief Lista przechowująca sklepy */

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;

    /**
     * \brief Metoda uruchamiana przy starcie obecnego Acrivity.
     * Metoda uruchamiana przy starcie obecnego Acrivity.
     * @param savedInstanceState zapisany stan instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_scanned_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //===========================================================
        //Lista Sklepów z produktem
        listView=(ListView)findViewById(R.id.scanned_listview);
        dataList = new ArrayList<>();
        dataList.add(new SingleStore("dsdsf", 4343.43));
        dataList.add(new SingleStore("dsdsf", 4343.43));
        dataList.add(new SingleStore("dsdsf", 4343.43));
        dataList.add(new SingleStore("dsdsf", 4343.43));
        dataList.add(new SingleStore("dsdsf", 4343.43));

        storeList = new StoreList(dataList, getApplicationContext());
        listView.setAdapter(storeList);
        //============================================================
        //GPS - nie dziala
        //Intent intent = new Intent(this, GPSTrackerActivity.class);
        //startActivityForResult(intent,69);

        //============================================================

    }

    /**
     * \brief Metoda, która powinna wyświetlić lokalizacje na mapie Google.
     * Metoda, która powinna wyświetlić lokalizacje na mapie Google.
     * @param view widok
     */
    public void goToMaps(View view){
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Szczecin" + " 71-270 " + "Klemensa Janickiego 24");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    /**
     * \brief Metoda uruchamiana na zakończenie obecnego activity.
     * Metoda uruchamiana na zakończenie obecnego activity.
     * @param RequestCode kod żądania
     * @param ResultCode kod wynikowy
     * @param data dane
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(RequestCode, ResultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("Scanner", "Cancelled scan");
                Toast.makeText(this, "Cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Scanner", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                //showProductDetails() //TODO: usunac po testach

                /*try {
                    Product product = api.getProductByProducerCode(result.getContents());
                    showProductDetails(product);


                }catch (FileNotFoundException e){

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }

            if(RequestCode == 69){
                Bundle extras = data.getExtras();
                Double longitude = extras.getDouble("Longitude");
                Double latitude = extras.getDouble("Latitude");
                Toast.makeText(this, "GEO: " + longitude+" "+latitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * \brief Metoda wykonuje akcje, jeśli połączono.
     * Metoda wykonuje akcje, jeśli połączono.
     * @param bundle pakiet
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    /**
     * \brief Metoda wykonuje akcje, jeśli połączenie zostało zawieszone.
     * Metoda wykonuje akcje, jeśli połączenie zostało zawieszone.
     * @param i liczba
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * \brief Metoda wykonuje akcje, jeśli próba połączenia się nie powiedzie.
     * Metoda wykonuje akcje, jeśli próba połączenia się nie powiedzie.
     * @param connectionResult rezultat połączenia
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * \brief Metoda wykonuje akcje, jeśli nastąpi zmiana lokalizacji.
     * Metoda wykonuje akcje, jeśli nastąpi zmiana lokalizacji.
     * @param location lokalizacja
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * \brief Metoda wykona akcje, jeśli gdy przechwytywanie wskaźnika jest włączone lub wyłączone dla bieżącego okna.
     * Metoda wykona akcje, jeśli gdy przechwytywanie wskaźnika jest włączone lub wyłączone dla bieżącego okna.
     * @param hasCapture czy kod został pobrany
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
