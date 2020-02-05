package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_mostra_struttura extends AppCompatActivity {

    private EditText campo_ricerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_struttura);

        campo_ricerca = findViewById(R.id.campo_ricerca);
    }

    public void Visualizza_recensioni_struttura(View view) {
    }

    public void pubblica_recensione(View view) {
    }

    public void Ricerca(View view) {
    }

    public void click_on_menù_laterale(View view) {
    }
}
