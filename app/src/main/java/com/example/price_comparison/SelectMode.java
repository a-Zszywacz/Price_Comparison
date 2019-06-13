package com.example.price_comparison;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//Class/View for devs
public class SelectMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
    }

    public void toGodMode(View view){
        Intent intent = new Intent(this, GodMode.class);
        startActivity(intent);
    }

    public void toUserMode(View view){
        Intent intent = new Intent(this, UserMode.class);
        startActivity(intent);
    }

}
