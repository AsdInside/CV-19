package com.e.cv_19.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Controllers.ControllerMain;
import com.e.cv_19.R;

public class Main_Activity extends AppCompatActivity {

    private EditText campo_ricerca;
    private RecyclerView lista_strutture;
    private ControllerMain Controller = new ControllerMain(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_strutture = findViewById(R.id.Strutture);
        Controller.mostra_strutture(lista_strutture);



    }


    protected void onStart(){
        super.onStart();
        Controller.mostra_lista();
    }

    protected void onStop(){
        super.onStop();
        Controller.togli_lista();
    }

    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            String nome = campo_ricerca.getText().toString();
            Controller.ricerca_per_nome(nome);
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura", Toast.LENGTH_SHORT).show();
        }
    }


    public void click_on_map(View view) {
        Controller.visualizza_mappa();
    }

    public void click_on_località_turistiche(View view) { Controller.ricerca_per_categoria("Tur"); }

    public void click_on_hotel(View view) {
        Controller.ricerca_per_categoria("Hot");
    }

    public void click_on_ristoranti(View view) {
        Controller.ricerca_per_categoria("Ris");
    }

    public void click_on_menù(View view) {
        Controller.mostra_menù();
    }
}
