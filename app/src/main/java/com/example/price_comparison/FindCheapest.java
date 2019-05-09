package com.example.price_comparison;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.ConcreteStore;
import com.example.price_comparison.api.Product;
import com.example.price_comparison.api.ProductInStore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindCheapest extends AppCompatActivity {

    ApiController api = new ApiController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_find_cheapest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Zeskanuj kod kreskowy",
                        Toast.LENGTH_SHORT).show();
                scanBarcode(v);
            }
        });

    }

    public void scanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
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

                try {
                    Product product = api.getProductByProducerCode(result.getContents());
                    showProductDetails(product);


                }catch (FileNotFoundException e){
                    TextView textView = findViewById(R.id.textView);
                    textView.setText("Produkt nieznaleziony");
                    textView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void showProductDetails(Product product) throws IOException, JSONException {

        TextView textView = findViewById(R.id.textView);
        textView.setText(product.getName());
        textView.setVisibility(View.VISIBLE);

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
        ArrayAdapter<String[]> arrayAdapter2;
        ListView listView = findViewById(R.id.listview_find_cheapest);
        listView.setVisibility(View.VISIBLE);
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

        arrayAdapter2 = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, text2){


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                String[] entry = text2.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(entry[0]);
                text2.setText(entry[1]);

                return view;
            }
        };
        listView.setAdapter(arrayAdapter2);

        //znajdz min price
        int indexMin=0;
        for(int i=0;i<prices.size();i++){
            if(prices.get(i)<prices.get(indexMin)){
                indexMin=i;
            }
        }
        TextView textView4 = findViewById(R.id.textView4);
        textView4.setVisibility(View.VISIBLE);

        TextView textView5 = findViewById(R.id.textView5);
        textView5.setVisibility(View.VISIBLE);
        textView5.setText(prices.get(indexMin)+" PLN");

        //znajdz max price
        int indexMax=0;
        for(int i=0;i<prices.size();i++){
            if(prices.get(i)>prices.get(indexMax)){
                indexMax=i;
            }
        }

        TextView textView6 = findViewById(R.id.textView6);
        textView6.setVisibility(View.VISIBLE);

        TextView textView7 = findViewById(R.id.textView7);
        textView7.setVisibility(View.VISIBLE);
        textView7.setText((((prices.get(indexMax)-prices.get(indexMin))*100)/100)+" PLN");

        TextView textView8 = findViewById(R.id.textView8);
        textView8.setVisibility(View.VISIBLE);


        TextView textView2 = findViewById(R.id.textView2);
        textView2.setVisibility(View.VISIBLE);
        textView2.setText("Najtaniej w "+concreteStoreMap.get(storeIds.get(indexMin)).getStoreName());

        TextView textView3 = findViewById(R.id.textView3);
        textView3.setVisibility(View.VISIBLE);
        textView3.setText("Proponowany sklep: "+concreteStoreMap.get(storeIds.get(indexMin)).getStreet());
    }

}
