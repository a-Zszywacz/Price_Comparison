package com.example.price_comparison;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//Class/View for devs

/**
 * Klasa pozwalająca na wybór trybu aplikacji.
 */
public class SelectMode extends AppCompatActivity {
    /**
     * Metoda uruchamiana przy starcie obecnego activity.
     * @param savedInstanceState zapisane stan instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
    }

    /**
     * Metoda uruchamia tryb "GOD" przeznaczony dla administratorów.
     * @param view widok
     */
    public void toGodMode(View view){
        Intent intent = new Intent(this, GodMode.class);
        startActivity(intent);
    }

    /**
     * Metoda uruchamia tryb "USER" przeznaczony dla użytkownika.
     * @param view widok
     */
    public void toUserMode(View view){
        Intent intent = new Intent(this, UserMode.class);
        startActivity(intent);
    }

}
