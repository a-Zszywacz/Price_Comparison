package com.example.price_comparison;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.Product;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
//import android.widget.Toolbar;

public class Database extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private ApiController apiController = new ApiController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setActionBar(toolbar);
        ArrayList<String> text = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        try {
            products = apiController.getAllProducts();

            for (Product product : products){
                text.add(product.getName());
            }
            text.add("dupa");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListView listView = findViewById(R.id.listview_database);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.scanner_list, text);
        listView.setAdapter(arrayAdapter);
    }
}
