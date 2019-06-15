package com.example.price_comparison;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.Product;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UserMode extends AppCompatActivity {

    ApiController api = new ApiController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_user_mode);

        ImageButton button = findViewById(R.id.usermode_btn1);

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

                Intent intent = new Intent(this, ScannedProduct.class);
                startActivity(intent);

                /*try {
                    Product product = api.getProductByProducerCode(result.getContents());
                    Intent intent = new Intent(this, ScannedProduct.class);
                    startActivity(intent);
                }catch (FileNotFoundException e){
                    Log.d("Scanner", "Nieznany produkt. Spróbuj ponownie");
                    Toast.makeText(this, "Scanned: " + "Nieznany produkt. Spróbuj ponownie", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        }
    }
}
