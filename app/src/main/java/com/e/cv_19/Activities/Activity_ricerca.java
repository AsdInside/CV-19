package com.e.cv_19.Activities;

import android.content.Intent;
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
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Hot");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }


    public void click_on_località_turistiche(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Tur");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_ristoranti(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Ris");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void Ricerca(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Nome Struttura",campo_ricerca.getText());
        Ricerca.putExtra("Tipo ricerca","Per nome");
        startActivity(Ricerca);
    }

    public void Ricerca_avanzata(View view) {
        /*ottenere i filtri dagli spinner e metterli negli extra*/

        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo ricerca","Avanzata");
        startActivity(Ricerca);
    }

    public void click_on_menù(View view) {
        Intent Intent_menù = new Intent(this,Activity_menu.class);
        startActivity(Intent_menù);
    }
}
