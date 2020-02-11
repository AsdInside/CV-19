package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Strutture;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Activity_risultati_ricerca extends AppCompatActivity {

    private RecyclerView risultati_ricerca;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        risultati_ricerca = findViewById(R.id.Risultati_ricerca);
        Bundle dati_ricevuti = getIntent().getExtras();
        String tipo_ricerca = dati_ricevuti.getString("Tipo ricerca");

        if(tipo_ricerca.equals("Avanzata")){

            effettua_ricerca_avanzata(dati_ricevuti.getString("Città"),dati_ricevuti.getString("Categoria"),dati_ricevuti.getString("Recensioni"));

        }else if(tipo_ricerca.equals("Category button")){
            effettua_ricerca_per_categoria(dati_ricevuti.getString("Tipo Struttura"));

        }else{
            effettua_ricerca_per_nome(dati_ricevuti.getString("Nome Struttura"));

        }

    }

    public void click_on_indietro(View view) {
        finish();
    }


    public void click_on_menù(View view) {
        Intent  Intent_menù = new Intent(this,Activity_menu.class);
        startActivity(Intent_menù);
    }


    public void effettua_ricerca_avanzata(String città,String categoria,String recensioni){



    }

    public void effettua_ricerca_per_categoria(String categoria){
        Query query = database.collection("Strutture").whereEqualTo("tipologia", categoria)
                .orderBy("valutazione", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>()
                .setQuery(query, Strutture.class)
                .build();

        StruttureAdapter adapter = new StruttureAdapter(options);
        risultati_ricerca.setAdapter(adapter);
        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));

    }

    public void effettua_ricerca_per_nome(String nome){
        Query query = database.collection("Strutture").whereEqualTo("nome", nome)
                .orderBy("valutazione", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>()
                .setQuery(query, Strutture.class)
                .build();

        StruttureAdapter adapter = new StruttureAdapter(options);
        risultati_ricerca.setAdapter(adapter);
        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
    }
}
