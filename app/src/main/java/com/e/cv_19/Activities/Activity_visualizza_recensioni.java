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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Activity_visualizza_recensioni extends AppCompatActivity {

    RecyclerView lista_recensioni;
    private RecensioniAdapter adapter;
    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference Recensioni = Database.collection("Recensione");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni);
        lista_recensioni = findViewById(R.id.ListaRecensioni);
        Configura_lista_recensioni();

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

    public void Configura_lista_recensioni(){
        FirebaseUser utente = mAuth.getCurrentUser();
        Query recensioni_utente = Recensioni.whereEqualTo("idAutore",utente.getUid());
        FirestoreRecyclerOptions<Recensioni> options= new FirestoreRecyclerOptions.Builder<Recensioni>()
                .setQuery(recensioni_utente, Recensioni.class).build();

        adapter = new RecensioniAdapter(options);

        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);
    }

}
