package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.R;
public class Activity_risultati_ricerca extends AppCompatActivity {

    private RecyclerView risultati_ricerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        risultati_ricerca = findViewById(R.id.Risultati_ricerca);
        Bundle dati_ricevuti = getIntent().getExtras();
        String tipo_ricerca = dati_ricevuti.getString("Tipo ricerca");

        if(tipo_ricerca.equals("Avanzata")){

        }else if(tipo_ricerca.equals("Category button")){
            String categoria = dati_ricevuti.getString("Tipo Struttura");

        }else{
            String nome = dati_ricevuti.getString("Nome Struttura");

        }

    }

    public void click_on_indietro(View view) {
        finish();
    }


    public void click_on_menù(View view) {
        Intent  Intent_menù = new Intent(this,Activity_menu.class);
        startActivity(Intent_menù);
    }
}
