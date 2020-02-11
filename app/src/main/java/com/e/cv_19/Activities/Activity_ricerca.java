package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;

public class Activity_ricerca extends AppCompatActivity {

    private EditText campo_ricerca;
    private Spinner seleziona_città;
    private Spinner seleziona_categoria;
    private Spinner seleziona_recensioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        seleziona_città = findViewById(R.id.spinnerCittà);
        seleziona_categoria = findViewById(R.id.spinnerCategoria);
        seleziona_recensioni = findViewById(R.id.spinnerRecensioni);
        setSpinners();


    }

    public void click_on_hotel(View view) {
        Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura", "Hot");
        Ricerca.putExtra("Tipo ricerca", "Category button");
        startActivity(Ricerca);
    }


    public void click_on_località_turistiche(View view) {
        Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura", "Tur");
        Ricerca.putExtra("Tipo ricerca", "Category button");
        startActivity(Ricerca);
    }

    public void click_on_ristoranti(View view) {
        Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura", "Ris");
        Ricerca.putExtra("Tipo ricerca", "Category button");
        startActivity(Ricerca);
    }

    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
            Ricerca.putExtra("Nome Struttura", campo_ricerca.getText());
            Ricerca.putExtra("Tipo ricerca", "Per nome");
            startActivity(Ricerca);
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkfilters(String città,String categoria,String recensioni){
        return TextUtils.isEmpty(categoria) || TextUtils.isEmpty(città) || TextUtils.isEmpty(recensioni);
    }

    public void Ricerca_avanzata(View view) {

        String categoria = seleziona_categoria.getSelectedItem().toString();
        String città = seleziona_città.getSelectedItem().toString();
        String recensioni = seleziona_recensioni.getSelectedItem().toString();

        if(checkfilters(città,categoria,recensioni)){
            Toast.makeText(this, "Inserire filtri ricerca",
                    Toast.LENGTH_SHORT).show();
        }else {
            Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
            Ricerca.putExtra("Tipo ricerca", "Avanzata");
            Ricerca.putExtra("Città", categoria);
            Ricerca.putExtra("Categoria", città);
            Ricerca.putExtra("Recensioni", recensioni);
            startActivity(Ricerca);
        }


    }

    public void click_on_menù(View view) {
        Intent Intent_menù = new Intent(this, Activity_menu.class);
        startActivity(Intent_menù);
    }

    public void setSpinners(){
        ArrayAdapter<CharSequence> adapter_città = ArrayAdapter.createFromResource(this, R.array.Città, android.R.layout.simple_spinner_dropdown_item);
        seleziona_città.setAdapter(adapter_città);
        ArrayAdapter<CharSequence> adapter_categoria = ArrayAdapter.createFromResource(this, R.array.Categoria, android.R.layout.simple_spinner_dropdown_item);
        seleziona_categoria.setAdapter(adapter_categoria);
        ArrayAdapter<CharSequence> adapter_recensioni = ArrayAdapter.createFromResource(this, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        seleziona_recensioni.setAdapter(adapter_recensioni);
    }
}
