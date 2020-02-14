package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.RecensioniAdapter;
import com.e.cv_19.Model.Recensioni;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Activity_visualizza_recensioni extends AppCompatActivity {

    private RecyclerView lista_recensioni;
    private RecensioniAdapter adapter;
    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    private CollectionReference Recensioni = Database.collection("Recensione");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni);
        Bundle dati_ricevuti = getIntent().getExtras();
        String utente = dati_ricevuti.getString("idUtente");


        lista_recensioni = findViewById(R.id.ListaRecensioni);
        Configura_lista_recensioni(utente);

    }

    public void click_on_indietro(View view) {
        finish();
    }

    public void Configura_lista_recensioni(String utente){
        Query recensioni_utente = Recensioni.whereEqualTo("idAutore",utente);
        FirestoreRecyclerOptions<Recensioni> options= new FirestoreRecyclerOptions.Builder<Recensioni>()
                .setQuery(recensioni_utente, Recensioni.class).build();

        adapter = new RecensioniAdapter(options);

        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);
    }

}
