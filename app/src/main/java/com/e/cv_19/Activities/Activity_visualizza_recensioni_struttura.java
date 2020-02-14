package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.RecensioniStrutturaAdapter;
import com.e.cv_19.Model.Recensione;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Activity_visualizza_recensioni_struttura extends AppCompatActivity {

    private Spinner filtro_voto;
    private Button segnala;
    private EditText campo_ricerca;
    private RecyclerView lista_recensioni;
    private RecensioniStrutturaAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id_struttura;
    private Query recensioni;
    private String idAutore;
    private String testo;
    private String recensione_selezionata;
    private CollectionReference notebookRef = db.collection("Recensione");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni_struttura);

        filtro_voto = findViewById(R.id.spinnerVoto);
        segnala = findViewById(R.id.buttonSegnala);
        ArrayAdapter<CharSequence> adapter_recensioni = ArrayAdapter.createFromResource(this, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        filtro_voto.setAdapter(adapter_recensioni);
        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_recensioni = findViewById(R.id.ListaRecensioni);
        Bundle dati_ricevuti = getIntent().getExtras();
        id_struttura = dati_ricevuti.getString("id");
        configurazione_lista_recensioni();
        adapter.setOnItemClickListner(new RecensioniStrutturaAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                idAutore = docSnapshot.getString("idAutore");
                recensione_selezionata = docSnapshot.getId();
                if(segnala.getVisibility() != View.VISIBLE){
                    segnala.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getApplicationContext(),"Recensione selezionata",Toast.LENGTH_SHORT).show();
            }
        });

    }


    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){

        super.onStop();
        adapter.stopListening();
    }


    private void configurazione_lista_recensioni() {
        recensioni = notebookRef.whereEqualTo("struttura",id_struttura);

        FirestoreRecyclerOptions<Recensione> options = new FirestoreRecyclerOptions.Builder<Recensione>().setQuery(recensioni,Recensione.class).build();
        adapter = new RecensioniStrutturaAdapter(options);


        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);
    }

    public void click_on_ristoranti(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Ris");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_località_turistiche(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Tur");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_hotel(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","Hot");
        Ricerca.putExtra("Tipo ricerca","Category button");
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

    public void click_on_filtra(View view) {
        String voto = filtro_voto.getSelectedItem().toString();
        if(voto.length() == 0){
            Toast.makeText(this, "Inserire il filtro dei voti", Toast.LENGTH_SHORT).show();
            return;
        }
        Query filtro = recensioni.whereEqualTo("voto",voto);

        FirestoreRecyclerOptions<Recensione> options = new FirestoreRecyclerOptions.Builder<Recensione>().setQuery(filtro,Recensione.class).build();
        adapter = new RecensioniStrutturaAdapter(options);


        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);
        onStart();
    }



    public void click_on_segnala(View view){
        final DocumentReference datiutente = db.collection("Utenti").document(idAutore);
        final DocumentReference recensione = notebookRef.document(recensione_selezionata);
        getTesto(recensione);

        datiutente.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot){
                if (documentSnapshot.exists()) {
                    final Map<String, Object> obj = new HashMap<>();
                    obj.put("nickname", documentSnapshot.getString("nickname"));
                    obj.put("struttura", id_struttura);
                    obj.put("testo",testo);
                    db.collection("Segnalazioni").add(obj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Segnalazione Inviata", Toast.LENGTH_SHORT).show();
                            Log.d("Segnalazioni", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                            Log.w("Segnalazioni", "Error adding document", e);
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Segnalazioni","segnalazione non invitata");
            }
        });


        segnala.setVisibility(View.GONE);
        idAutore = null;

    }

    private void getTesto(DocumentReference recensione) {
        recensione.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    testo = documentSnapshot.getString("testo");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("testo","testo non trovato");
            }
        });
    }

    public void click_on_menù(View view) {
        Intent intent_menù = new Intent(this,Activity_menu.class);
        startActivity(intent_menù);
    }
}
