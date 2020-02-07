package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Main_Activity extends AppCompatActivity {

    private TextView campo_ricerca;
    private ListView lista_strutture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_strutture = findViewById(R.id.Strutture);
    }

    public void Ricerca(View view) {
    }

    public void click_on_map(View view) {
    }

    public void click_on_località_turistiche(View view) {
    }

    public void click_on_menù(View view) {
    }

    public void click_on_hotel(View view) {
    }

    public void click_on_ristoranti(View view) {
    }
}
