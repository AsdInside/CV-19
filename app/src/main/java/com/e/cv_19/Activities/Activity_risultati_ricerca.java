package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Strutture;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Activity_risultati_ricerca extends AppCompatActivity {

    private RecyclerView risultati_ricerca;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private StruttureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        risultati_ricerca = findViewById(R.id.strutture_ricercate);
        Bundle dati_ricevuti = getIntent().getExtras();
        String tipo_ricerca = dati_ricevuti.getString("Tipo ricerca");

        if(tipo_ricerca.equals("Avanzata")){

            effettua_ricerca_avanzata(dati_ricevuti.getString("Città"),dati_ricevuti.getString("Categoria"),dati_ricevuti.getString("Recensioni"));

        }else if(tipo_ricerca.equals("Category button")){
            effettua_ricerca_per_categoria(dati_ricevuti.getString("Tipo Struttura"));

        }else{
            effettua_ricerca_per_nome(dati_ricevuti.getString("Nome Struttura"));

        }

        adapter.setOnItemClickListner(new StruttureAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                gotoPage(id_struttura);
            }
        });

    }

    private void gotoPage(String id_struttura) {
        Intent mostra_struttura = new Intent(this, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        startActivity(mostra_struttura);
    }


    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){

        super.onStop();
        adapter.stopListening();
    }

    public void click_on_indietro(View view) {
        finish();
    }


    public void click_on_menù(View view) {
        Intent  Intent_menù = new Intent(this,Activity_menu.class);
        startActivity(Intent_menù);
    }


    public void effettua_ricerca_avanzata(String città,String categoria,String recensioni){
        Query strutture = notebookRef.whereEqualTo("tipologia",categoria);
        Query filtro_città = strutture.whereEqualTo("citta",città);
        Query risultato = filtro_città.whereGreaterThanOrEqualTo("valutazione",recensioni);

        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(risultato,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
        risultati_ricerca.setAdapter(adapter);



    }

    public void effettua_ricerca_per_categoria(String categoria){

        Query strutture = notebookRef.whereEqualTo("tipologia",categoria);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(strutture,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
        risultati_ricerca.setAdapter(adapter);

    }

    public void effettua_ricerca_per_nome(String nome){
        Query query = notebookRef.whereEqualTo("nome", nome);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>()
                .setQuery(query, Strutture.class)
                .build();

        StruttureAdapter adapter = new StruttureAdapter(options);
        risultati_ricerca.setAdapter(adapter);
        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
    }
}
