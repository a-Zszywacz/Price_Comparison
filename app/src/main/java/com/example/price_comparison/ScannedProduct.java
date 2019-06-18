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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.ConcreteStore;
import com.example.price_comparison.api.Product;
import com.example.price_comparison.api.ProductInStore;
import com.example.price_comparison.customlist.SingleProduct;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScannedProduct extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    ApiController api = new ApiController();

    ArrayList<SingleStore> dataList;
    ListView listView;
    private static StoreList storeList;
    private MySQLite db;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;

    private String cheaperStoreLocation;

    String productName;
    String productPrice;
    String storeName;
    String storeAddress;

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
        db=new MySQLite(this);
        setViewTexts();

        ImageButton scaneAgain = findViewById(R.id.scan_again_btn);
        scaneAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Zeskanuj kod kreskowy",
                        Toast.LENGTH_SHORT).show();
                scanBarcode(view);
            }
        });

        ImageButton clickButton = (ImageButton) findViewById(R.id.imageButton3);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.dodaj(new SingleProduct(productName,productPrice,storeName,storeAddress));
                Toast.makeText(ScannedProduct.this, "Dodano: " + "Produkt został usunięty", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void goToCart(View view){
        //Sprawdzić czy w Cart jest już jakiś produkt. Jesli nie to wyswietlic komunikat
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
    }

    private void setViewTexts() {
        try {

            Product product = (Product) getIntent().getSerializableExtra("PRODUCT");
            //[Nazwa produktu]
            TextView productName = findViewById(R.id.product_name1);
            productName.setText(product.getName());
            //========================
            //========================

            //pro tip: Wywali FileNotFoundException jesli nie bedzi go nigdzie w sklepie
            ArrayList<ConcreteStore> concreteStores = api.getStoresByProducerCode(product.getProducerCode());
            Map<Integer, ArrayList<ProductInStore>> productInStores = new HashMap<>();
            for(ConcreteStore concreteStore : concreteStores){
                productInStores.put(concreteStore.getId(), api.getConreteProductsByStoreID(concreteStore.getId()));
            }
            ArrayList<Float> prices= new ArrayList<>();
            ArrayList<Integer> storeIds=new ArrayList<>();

            for (int key : productInStores.keySet()){
                storeIds.add(key);
                for(ProductInStore productInStore : productInStores.get(key)){
                    if(productInStore.getProducerCode().equals(product.getProducerCode())){
                        prices.add(productInStore.getPrice());
                    }
                }
            }

            final ArrayList<String[]> text2 = new ArrayList<>();

            Map<Integer, ConcreteStore> concreteStoreMap = new HashMap<>();
            for(ConcreteStore concreteStore : concreteStores){
                for (int i : storeIds){
                    if(concreteStore.getId() == i){
                        concreteStoreMap.put(i, concreteStore);
                    }
                }
            }

            for (int i=0; i<prices.size();i++){
                text2.add(new String[]{concreteStoreMap.get(storeIds.get(i)).getStoreName(),prices.get(i).toString()});
            }

            //======================

            //[Nazwa sklepu lokalnego]
            TextView localStore = findViewById(R.id.local_strore18);
            localStore.setText(text2.get(0)[0]);
            storeName = text2.get(0)[0];
            storeAddress = "";

            //[x] zł
            TextView localPrice = findViewById(R.id.local_price);
            productPrice = text2.get(0)[1];
            localPrice.setText(text2.get(0)[1]+"zł");


            listView=(ListView)findViewById(R.id.scanned_listview);
            dataList = new ArrayList<>();
            for(String[] strs : text2){
                dataList.add(new SingleStore(strs[0], strs[1]+"zł"));
            }
            storeList = new StoreList(dataList, getApplicationContext());
            listView.setAdapter(storeList);
            //==========================
            //znajdz min price
            int indexMin=0;
            for(int i=0;i<prices.size();i++){
                if(prices.get(i)<prices.get(indexMin)){
                    indexMin=i;
                }
            }
            //[Najt Cena]
            TextView cheaperPrice= findViewById(R.id.cheaper_price);
            cheaperPrice.setText(prices.get(indexMin)+" PLN");

            //[cena oszcz]
            TextView savePrice = findViewById(R.id.save_price);
            BigDecimal x = new BigDecimal(text2.get(0)[1]);

            //BigDecimal x = new BigDecimal(text2.get(0)[1]);
            savePrice.setText(x.subtract(new BigDecimal(prices.get(indexMin))).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            //[Nazwa najt sklep]
            TextView cheaperStoreName=findViewById(R.id.cheaper_store_name);
            cheaperStoreName.setText(concreteStoreMap.get(storeIds.get(indexMin)).getStoreName());
            cheaperStoreLocation = concreteStoreMap.get(storeIds.get(indexMin)).getCity() +
                    " " + concreteStoreMap.get(storeIds.get(indexMin)).getPostCode() +
                    " " + concreteStoreMap.get(storeIds.get(indexMin)).getStreet() ;
            //[lokalizacja najt sklepu]
            TextView cheaperStoreLocalisation = findViewById(R.id.cheaper_store_localisation);
            cheaperStoreLocalisation.setText(
                    concreteStoreMap.get(storeIds.get(indexMin)).getCity() +
                            " " + concreteStoreMap.get(storeIds.get(indexMin)).getPostCode() +
                            " " + concreteStoreMap.get(storeIds.get(indexMin)).getStreet()
            );


            //======================
            //ArrayList<ConcreteStore> concreteStores = api.getStoresByProducerCode(product.getProducerCode());
            //[Nazwa sklepu lokalnego]
            TextView localStoreName = findViewById(R.id.product_name1);
            productName.setText(product.getName());
            this.productName = product.getName();


        } catch (JSONException e) {
            Log.d("JSON_Err", "JSON ERROR");
            Toast.makeText(this, "JSON_Err: " + "JSON ERROR", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goToMaps(View view){
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + cheaperStoreLocation);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

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

                try {
                    Product product = api.getProductByProducerCode(result.getContents());
                    Intent intent = new Intent(this, ScannedProduct.class);
                    intent.putExtra("PRODUCT", product);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }catch (FileNotFoundException e){
                    Log.d("Scanner", "Nieznany produkt. Spróbuj ponownie");
                    Toast.makeText(this, "Scanned: " + "Nieznany produkt. Spróbuj ponownie", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(RequestCode == 69){
                Bundle extras = data.getExtras();
                Double longitude = extras.getDouble("Longitude");
                Double latitude = extras.getDouble("Latitude");
                Toast.makeText(this, "GEO: " + longitude+" "+latitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

/*    public void addProductToList(View view){
        db.dodaj(new SingleProduct(productName,productPrice,storeName,storeAddress));
    }*/

}
