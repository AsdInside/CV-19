package com.e.cv_19.Activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerStruttura;
import com.e.cv_19.R;




public class Activity_mostra_struttura extends AppCompatActivity {

    private EditText campo_ricerca;
    private EditText testo_recensione;
    private TextView nome_struttura;
    private TextView descrizione_struttura;
    private ImageView immagine_struttura;
    private Spinner voto_recensione;
    private ControllerStruttura Controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_struttura);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        testo_recensione= findViewById(R.id.editTextTestoRecensione);
        voto_recensione = findViewById(R.id.spinnerVoto);
        immagine_struttura = findViewById(R.id.imageStruttura);
        nome_struttura = findViewById(R.id.textViewNomeStruttura);
        descrizione_struttura = findViewById(R.id.textViewDescrizione);

        Controller = new ControllerStruttura(getIntent().getExtras().getString("id"));
        Controller.configuraSpinner(this,voto_recensione);
        Controller.mostra_struttura(nome_struttura,descrizione_struttura,immagine_struttura);


    }





    public void Visualizza_recensioni_struttura(View view) { Controller.mostra_recensioni_struttura(this); }

    public void pubblica_recensione(View view) {
        Controller.pubblica_recensione(this,testo_recensione.getText().toString(),voto_recensione.getSelectedItem().toString());

    }


    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            Controller.ricerca_per_nome(campo_ricerca.getText().toString(),this);
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura", Toast.LENGTH_SHORT).show();
        }
    }


    public void click_on_menù(View view) { ControllerStruttura.mostra_menù(this);}
}
