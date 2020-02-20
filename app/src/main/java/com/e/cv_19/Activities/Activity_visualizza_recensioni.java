package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Controllers.ControllerMain;
import com.e.cv_19.R;


public class Activity_visualizza_recensioni extends AppCompatActivity {

    RecyclerView lista_recensioni;
    private ControllerMain Controller = new ControllerMain(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni);
        lista_recensioni = findViewById(R.id.ListaRecensioni);
        Controller.mostra_recensioni_personali(lista_recensioni);
    }

    protected void onStart(){
        super.onStart();
        Controller.mostra_recensioni();
    }

    protected void onStop(){

        super.onStop();
        Controller.togli_recensioni();
    }

    public void click_on_indietro(View view) {
        finish();
    }



}
