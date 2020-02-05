package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Activity_visualizza_recensioni extends AppCompatActivity {

    private ListView lista_recensioni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni);

        lista_recensioni = findViewById(R.id.ListaRecensioni);
        //TODO scrivere il codice dell'adapter di lista_recensioni

    }

    public void click_on_indietro(View view) {
        finish();
    }
}
