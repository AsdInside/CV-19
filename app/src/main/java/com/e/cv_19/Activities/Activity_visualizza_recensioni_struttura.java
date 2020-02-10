package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
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
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Ris");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_località_turistiche(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Tur");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_hotel(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Hot");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void Ricerca(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Nome Struttura",campo_ricerca.getText());
        Ricerca.putExtra("Tipo ricerca","Per nome");
        startActivity(Ricerca);
    }

    public void click_on_filtra(View view) {
    }

    public void click_on_segnala(View view){
        //potrebbe essere necessario spostare questo metodo nell'adapter e cambiare il context del
        //layout riga recensioni struttura
    }

    public void click_on_menù(View view) {
        Intent intent_menù = new Intent(this,Activity_menu.class);
        startActivity(intent_menù);
    }
}
