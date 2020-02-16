package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Strutture;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

        }else{
            effettua_ricerca_per_categoria(dati_ricevuti.getString("Tipo Struttura"));

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
        Query risultato = costruzione_query(città,categoria,recensioni);

        risultato.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Nessun risultato",Toast.LENGTH_SHORT).show();
            }
        });

        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(risultato,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
        risultati_ricerca.setAdapter(adapter);



    }

    private String converti_categoria(String categoria){
        if(categoria.equals("Ristorante")){
            return "Ris";
        }else if(categoria.equals("Hotel")){
            return "Hot";
        }else{
            return "Tur";
        }
    }


    private Query costruzione_query(String città, String categoria, String recensioni) {
        Query filtro_città;
        Query filtro_categoria;
        Query risultato;

        if(filtri_non_inseriti(città,categoria,recensioni)){
            return notebookRef.orderBy("valutazione", Query.Direction.DESCENDING);
        }

        if(!città.isEmpty()){
            filtro_città = notebookRef.whereEqualTo("citta",città);
            if(!categoria.isEmpty()){
                filtro_categoria = filtro_città.whereEqualTo("tipologia",converti_categoria(categoria));
                if(!recensioni.isEmpty()){
                    risultato = filtro_categoria.whereGreaterThanOrEqualTo("valutazione",Double.parseDouble(recensioni));
                }else{
                    return filtro_categoria;
                }
            }else{
                if(!recensioni.isEmpty()){
                    risultato = filtro_città.whereGreaterThanOrEqualTo("valutazione",Double.parseDouble(recensioni));
                }else{
                    return filtro_città;
                }
            }
        }else{
            if(!categoria.isEmpty()){
                filtro_categoria = notebookRef.whereEqualTo("tipologia",converti_categoria(categoria));
                if(!recensioni.isEmpty()){
                    risultato = filtro_categoria.whereGreaterThanOrEqualTo("valutazione",Double.parseDouble(recensioni));
                }else{
                    return filtro_categoria;
                }
            }else{
                return notebookRef.whereGreaterThanOrEqualTo("valutazione",Double.parseDouble(recensioni));
            }
        }

        return risultato;
    }

    private boolean filtri_non_inseriti(String città, String categoria, String recensioni) {
        return città.isEmpty() && categoria.isEmpty() && recensioni.isEmpty();
    }

    public void effettua_ricerca_per_categoria(String categoria){

        Query strutture = notebookRef.whereEqualTo("tipologia",categoria);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(strutture,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(this));
        risultati_ricerca.setAdapter(adapter);

    }

}
