package com.e.cv_19.Activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Controllers.ControllerStruttura;
import com.e.cv_19.R;


public class Activity_visualizza_recensioni_struttura extends AppCompatActivity {

    private Spinner filtro_voto;
    private Button segnala;
    private EditText campo_ricerca;
    private RecyclerView lista_recensioni;
    private ControllerStruttura Controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni_struttura);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        filtro_voto = findViewById(R.id.spinnerVoto);
        segnala = findViewById(R.id.buttonSegnala);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_recensioni = findViewById(R.id.ListaRecensioni);


        Controller = new ControllerStruttura(getIntent().getExtras().getString("id"),this);
        Controller.setSpinner(filtro_voto);
        Controller.configurazione_lista_recensioni(lista_recensioni,segnala);

    }


    protected void onStart(){
        super.onStart();
        Controller.mostra_recensioni();
    }

    protected void onStop(){

        super.onStop();
        Controller.togli_recensioni();
    }

    public void click_on_ristoranti(View view) { Controller.ricerca_per_categoria("Ris");}

    public void click_on_località_turistiche(View view) { Controller.ricerca_per_categoria("Tur"); }

    public void click_on_hotel(View view) {Controller.ricerca_per_categoria("Hot");}



    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            Controller.ricerca_per_nome(campo_ricerca.getText().toString());
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura", Toast.LENGTH_SHORT).show();
        }
    }

    public void click_on_filtra(View view) {
        String voto = filtro_voto.getSelectedItem().toString();
        if(voto.length() == 0){
            Toast.makeText(this, "Inserire il filtro dei voti", Toast.LENGTH_SHORT).show();
            return;
        }

        Controller.filtra_recensioni(lista_recensioni,voto);


    }



    public void click_on_segnala(View view){
        Controller.segnala_recensione();
    }




    public void click_on_menù(View view) {
        Controller.mostra_menù();
    }
}
