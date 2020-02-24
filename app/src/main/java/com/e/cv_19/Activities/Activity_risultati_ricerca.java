package com.e.cv_19.Activities;


import android.os.Bundle;
import android.view.View;




import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.e.cv_19.Controllers.ControllerRicerca;
import com.e.cv_19.R;



public class Activity_risultati_ricerca extends AppCompatActivity {

    private RecyclerView risultati_ricerca;
    private ControllerRicerca Controller = new ControllerRicerca(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        risultati_ricerca = findViewById(R.id.strutture_ricercate);
        Controller.effettua_ricerca(getIntent().getExtras(),risultati_ricerca);
    }



    protected void onStart(){
        super.onStart();
        Controller.mostra_risultati();
    }

    protected void onStop(){

        super.onStop();
        Controller.togli_risultati();
    }

    public void click_on_indietro(View view) {
        finish();
    }


    public void click_on_menù(View view) { Controller.mostra_menù(); }

}
