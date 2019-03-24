package com.example.price_comparison;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class Scanner extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayList<String> resultCodes;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Try to scan bardcode",
                        Toast.LENGTH_SHORT).show();
                scanBarcode(view);
            }
        });

        update();

    }
    public void update(){
        if( resultCodes == null){
            resultCodes = new ArrayList<>();
            ListView listView = findViewById(R.id.list);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.scanner_list, resultCodes);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                   if(position==0){
                        //createIntentForItem(view);
                       createDialogForItem(resultCodes.get(position),position);
                   }
               }
            });
        }
        else {
            ListView listView = findViewById(R.id.list);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.scanner_list, resultCodes);
            listView.setAdapter(arrayAdapter);
        }
    }

    public void createDialogForItem(String barCode, final int index){
        final Dialog dialog = new Dialog(Scanner.this);
        dialog.setTitle("Edycja Itemu:");
        dialog.setContentView(R.layout.scanner_dialog_box);
        TextView txtMessage = (TextView)dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("Update item");
        txtMessage.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText = (EditText) dialog.findViewById(R.id.txtinput);
        editText.setText(barCode);
        Button bt= (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultCodes.set(index, editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void createIntentForItem(View view){
        Intent myIntent = new Intent(view.getContext(), ScannerEditListItem.class);
        startActivityForResult(myIntent,0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("resultCodes", resultCodes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultCodes = savedInstanceState.getStringArrayList("resultCodes");
        update();
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

                if( resultCodes == null) {
                    resultCodes = new ArrayList<>();
                    resultCodes.add(result.getContents());
                    update();
                }
                else {
                    resultCodes.add(result.getContents());
                    update();
                }
                //list();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.fab:
                //Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);
                break;
            case R.id.action_settings:Toast.makeText(getApplicationContext(),
                    "Kliknięto przycisk Obiekt2",
                    Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
