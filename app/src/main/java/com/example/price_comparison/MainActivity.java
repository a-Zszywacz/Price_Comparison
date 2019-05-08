package com.example.price_comparison;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void toScanner(View view){
        Intent intencja = new Intent(this, Scanner.class);
        startActivity(intencja);
        //Toast.makeText(getApplicationContext(), "Kliknięto przycisk Login", Toast.LENGTH_SHORT).show();
    }
    public void toDatabase(View view){
        Intent intencja = new Intent(this, Database.class);
        startActivity(intencja);
        //Toast.makeText(getApplicationContext(), "Kliknięto przycisk Login", Toast.LENGTH_SHORT).show();
    }

    public void toFindCheapest(View view){
        Intent intent = new Intent(this, FindCheapest.class);
        startActivity(intent);
    }

}
