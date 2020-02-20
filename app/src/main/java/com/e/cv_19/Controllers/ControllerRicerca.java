package com.e.cv_19.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Activities.Activity_menu;
import com.e.cv_19.Activities.Activity_mostra_struttura;
import com.e.cv_19.Activities.Activity_risultati_ricerca;
import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Strutture;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ControllerRicerca {
    private AppCompatActivity Context;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private StruttureAdapter adapter;



    public ControllerRicerca(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodi per Activity_ricerca

    public void setSpinners(Spinner seleziona_città, Spinner seleziona_categoria, Spinner seleziona_recensioni) {
        ArrayAdapter<CharSequence> adapter_città = ArrayAdapter.createFromResource(Context, R.array.Città, android.R.layout.simple_spinner_dropdown_item);
        seleziona_città.setAdapter(adapter_città);
        ArrayAdapter<CharSequence> adapter_categoria = ArrayAdapter.createFromResource(Context, R.array.Categoria, android.R.layout.simple_spinner_dropdown_item);
        seleziona_categoria.setAdapter(adapter_categoria);
        ArrayAdapter<CharSequence> adapter_recensioni = ArrayAdapter.createFromResource(Context, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        seleziona_recensioni.setAdapter(adapter_recensioni);
    }


    public void ricerca_per_categoria(String tipo_struttura){
        Intent Ricerca = new Intent(Context, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura",tipo_struttura);
        Ricerca.putExtra("Tipo ricerca","Category button");
        Context.startActivity(Ricerca);

    }

    public void ricerca_per_nome(final String nome){
        notebookRef.orderBy("valutazione", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                        if(document.getString("nome").equalsIgnoreCase(nome)){
                            Struttura_selezionata(document.getId());

                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Context,"nessuna struttura trovata",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Struttura_selezionata(String id_struttura) {
        Intent mostra_struttura = new Intent(Context, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        Context.startActivity(mostra_struttura);
    }

    public void mostra_menù() {
        Intent Intent_menù = new Intent(Context, Activity_menu.class);
        Context.startActivity(Intent_menù);
    }

    public void avvia_ricerca_avanzata(String categoria, String città, String recensioni) {


        Intent Ricerca = new Intent(Context, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo ricerca", "Avanzata");
        Ricerca.putExtra("Città", città);
        Ricerca.putExtra("Categoria", categoria);
        Ricerca.putExtra("Recensioni", recensioni);
        Context.startActivity(Ricerca);
    }

    public void effettua_ricerca(Bundle dati_ricevuti, RecyclerView risultati_ricerca) {

        String tipo_ricerca = dati_ricevuti.getString("Tipo ricerca");

        if(tipo_ricerca.equals("Avanzata")){

            effettua_ricerca_avanzata(dati_ricevuti.getString("Città"),dati_ricevuti.getString("Categoria")
                    ,dati_ricevuti.getString("Recensioni"),risultati_ricerca);

        }else{
            effettua_ricerca_per_categoria(dati_ricevuti.getString("Tipo Struttura"),risultati_ricerca);

        }


        adapter.setOnItemClickListner(new StruttureAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Struttura_selezionata(id_struttura);
            }
        });
    }

    private void effettua_ricerca_avanzata(String città, String categoria, String recensioni, RecyclerView risultati_ricerca) {
        Query risultato = costruzione_query(città,categoria,recensioni);

        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(risultato,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(Context));
        risultati_ricerca.setAdapter(adapter);

    }

    private void effettua_ricerca_per_categoria(String tipo_struttura, RecyclerView risultati_ricerca) {
        Query strutture = notebookRef.whereEqualTo("tipologia",tipo_struttura);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(strutture,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        risultati_ricerca.setHasFixedSize(true);
        risultati_ricerca.setLayoutManager(new LinearLayoutManager(Context));
        risultati_ricerca.setAdapter(adapter);


    }

    public void mostra_risultati() {
        adapter.startListening();
    }

    public void togli_risultati() {
        adapter.stopListening();
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
}
