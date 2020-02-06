package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Activity_visualizza_recensioni_struttura extends AppCompatActivity {

    private Spinner filtro_voto;
    private EditText campo_ricerca;
    private ListView lista_recensioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni_struttura);

        filtro_voto = findViewById(R.id.spinnerVoto);
        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_recensioni = findViewById(R.id.ListaRecensioni);

    }

    public void click_on_ristoranti(View view) {
    }

    public void click_on_località_turistiche(View view) {
    }

    public void click_on_menù_laterale(View view) {
    }

    public void click_on_hotel(View view) {
    }

    public void Ricerca(View view) {
    }

    public void click_on_filtra(View view) {
    }

    public void click_on_segnala(View view){

    }
}
