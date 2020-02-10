package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;

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
        String testo = testo_recensione.getText().toString();
        /*estrarre il voto dallo spinner*/

    }

    public void Ricerca(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Nome Struttura",campo_ricerca.getText());
        Ricerca.putExtra("Tipo ricerca","Per nome");
        startActivity(Ricerca);
    }


    public void click_on_menù(View view) {
        Intent menù = new Intent(this ,Activity_menu.class);
        startActivity(menù);
    }
}
