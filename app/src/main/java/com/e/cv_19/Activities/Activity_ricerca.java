package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;

public class Activity_ricerca extends AppCompatActivity {

    private EditText campo_ricerca;
    private Spinner seleziona_città;
    private Spinner seleziona_categoria;
    private Spinner seleziona_recensioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        seleziona_città = findViewById(R.id.spinnerCittà);
        seleziona_categoria = findViewById(R.id.spinnerCategoria);
        seleziona_recensioni = findViewById(R.id.spinnerRecensioni);
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
