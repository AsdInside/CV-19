package com.e.cv_19.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerRicerca;
import com.e.cv_19.R;


public class Activity_ricerca extends AppCompatActivity {

    private EditText campo_ricerca;
    private Spinner seleziona_città;
    private Spinner seleziona_categoria;
    private Spinner seleziona_recensioni;
    private ControllerRicerca Controller= new ControllerRicerca(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        seleziona_città = findViewById(R.id.spinnerCittà);
        seleziona_categoria = findViewById(R.id.spinnerCategoria);
        seleziona_recensioni = findViewById(R.id.spinnerRecensioni);
        Controller.setSpinners(seleziona_città,seleziona_categoria,seleziona_recensioni);



    }

    public void click_on_hotel(View view) { Controller.ricerca_per_categoria("Hot"); }


    public void click_on_località_turistiche(View view) { Controller.ricerca_per_categoria("Tur"); }

    public void click_on_ristoranti(View view) { Controller.ricerca_per_categoria("Ris"); }


    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            Controller.ricerca_per_nome(campo_ricerca.getText().toString());
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura",
                    Toast.LENGTH_SHORT).show();
        }
    }



    public void Ricerca_avanzata(View view) {

        Controller.avvia_ricerca_avanzata(seleziona_categoria.getSelectedItem().toString(),
                seleziona_città.getSelectedItem().toString(),seleziona_recensioni.getSelectedItem().toString());
    }

    public void click_on_menù(View view) { Controller.mostra_menù(); }

}
