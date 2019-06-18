package com.example.price_comparison;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.price_comparison.api.ApiController;
import com.example.price_comparison.api.Product;
import com.example.price_comparison.customlist.ProductList;
import com.example.price_comparison.customlist.SingleProduct;
import com.example.price_comparison.customlist.SingleStore;
import com.example.price_comparison.customlist.StoreList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    ArrayList<SingleProduct> dataList;
    ListView listView;
    private static ProductList storeList;
    private MySQLite db;
    ApiController api = new ApiController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        /*listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView name = view.findViewById(android.R.id.text1);
                        db.usun(name.getText().toString());
                        storeList.changeCursor(db.lista());
                        storeList.notifyDataSetChanged();
                        return true;
                    }
                }
        );*/
        ImageButton scaneAgain2 = findViewById(R.id.imageButton);
        scaneAgain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete();
                Intent intent = new Intent(Cart.this, Cart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

    }

    private void setViewTexts() {
        db=new MySQLite(this);
        //db.dodaj(new SingleProduct("nazwa","129","aaa","bbb"));
        List<SingleProduct> singleProduct = db.getProducts();
        listView=(ListView)findViewById(R.id.scanned_listview);
        dataList = new ArrayList<>();
        //BigDecimal a= new BigDecimal(0);
        float sum = 0;
        boolean isFirst=true;

        for (SingleProduct singleProduct1 : singleProduct){
            dataList.add(singleProduct1);
            if(isFirst){
                //a= new BigDecimal(singleProduct1.getPrice());
                sum += Float.parseFloat(singleProduct1.getPrice());
                isFirst=false;
                continue;
            }
            sum += Float.parseFloat(singleProduct1.getPrice());
            //a.add(new BigDecimal(singleProduct1.getPrice()));
        }
        //dataList.add(new SingleProduct("Lidl", "100000"+"zł"));
        //dataList.add(new SingleProduct("Lidl", "100000"+"zł"));
        //dataList.add(new SingleProduct("Lidl", "100000"+"zł"));

        storeList = new ProductList(dataList, getApplicationContext());
        listView.setAdapter(storeList);

        TextView textViewy = findViewById(R.id.cheaper_price21);
        BigDecimal a = new BigDecimal(sum);
        textViewy.setText((a.setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
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


}
