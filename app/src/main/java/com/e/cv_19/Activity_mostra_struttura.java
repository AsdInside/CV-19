package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Activity_mostra_struttura extends AppCompatActivity {

    private EditText campo_ricerca;
    private EditText testo_recensione;
    private Spinner voto_recensione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_struttura);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        testo_recensione= findViewById(R.id.editTextTestoRecensione);
        voto_recensione = findViewById(R.id.spinnerVoto);
    }

    public void Visualizza_recensioni_struttura(View view) {
    }

    public void pubblica_recensione(View view) {
    }

    public void Ricerca(View view) {
    }

    public void click_on_menù(View view) {
    }
}
