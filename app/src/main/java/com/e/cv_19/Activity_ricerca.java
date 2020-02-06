package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_ricerca extends AppCompatActivity {

    private EditText campo_ricerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);

        campo_ricerca = findViewById(R.id.campo_ricerca);
    }

    public void click_on_hotel(View view) {
    }

    public void click_on_menù_laterale(View view) {
    }

    public void click_on_località_turistiche(View view) {
    }

    public void click_on_ristoranti(View view) {
    }

    public void Ricerca(View view) {
    }

    public void Ricerca_avanzata(View view) {
    }
}
