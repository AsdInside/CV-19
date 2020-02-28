package com.e.cv_19.Controllers;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Activities.Activity_menu;
import com.e.cv_19.Activities.Activity_mostra_struttura;
import com.e.cv_19.Activities.Activity_risultati_ricerca;
import com.e.cv_19.Activities.Activity_visualizza_recensioni_struttura;
import com.e.cv_19.Adapter.RecensioniStrutturaAdapter;
import com.e.cv_19.Model.Recensioni;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ControllerStruttura {
    private AppCompatActivity Context;
    private String testo;
    private String idAutore;
    private String recensione_selezionata;
    private Button segnala;
    private String id_struttura;
    private Query recensioni;
    private boolean flag;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private RecensioniStrutturaAdapter adapter;
    private double voti=0;
    private int count=0;
    private double totale=0;
    private double media=0;


    public ControllerStruttura(String id,AppCompatActivity Activity){
        id_struttura = id;
        Context = Activity;
        if(Context.getClass() == Activity_mostra_struttura.class){
            flag = recensione_gia_scritta(mAuth.getCurrentUser().getUid());
        }

    }

    //Metodi per Activity_mostra_struttura

    public void mostra_menù( ) {
        Intent menù = new Intent(Context , Activity_menu.class);
        Context.startActivity(menù);
    }

    public void configuraSpinner(Spinner voto_recensione) {
        ArrayAdapter<CharSequence> adapter_voto = ArrayAdapter.createFromResource(Context, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        voto_recensione.setAdapter(adapter_voto);
    }

    public void mostra_struttura(final TextView nome_struttura,final TextView descrizione_struttura,final ImageView immagine_struttura) {
        final DocumentReference datiStruttura = notebookRef.document(id_struttura);

        datiStruttura.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        nome_struttura.setText(document.getString("nome"));
                        descrizione_struttura.setText(document.getString("descrizione"));
                        Picasso.get().load(document.getString("immagine")).into(immagine_struttura);
                    }

                }
            }
        });

    }


    public void mostra_recensioni_struttura() {
        Intent Recensioni_struttura = new Intent(Context, Activity_visualizza_recensioni_struttura.class);
        Recensioni_struttura.putExtra("id",id_struttura);
        Context.startActivity(Recensioni_struttura);
    }

    public void pubblica_recensione(String testo, String voto) {
        if(!flag){
            Toast.makeText(Context, "Hai già recensito questa struttura", Toast.LENGTH_SHORT).show();
            return;
        }
        if(IsValidRank(voto) && IsValidText(testo)){
            String user_id = mAuth.getCurrentUser().getUid();
            if(user_id.length() == 0){
                Toast.makeText(Context, "IdAutore non trovato", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String,Object> nuova_recensione = new HashMap<>();
            nuova_recensione.put("testo",testo);
            nuova_recensione.put("voto",Integer.parseInt(voto));
            nuova_recensione.put("idAutore",user_id);
            nuova_recensione.put("struttura",id_struttura);




            database.collection("Recensione").add(nuova_recensione)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Context, "Recensione pubblicata", Toast.LENGTH_SHORT).show();
                            Log.d("Recensione", "DocumentSnapshot added with ID: " + documentReference.getId());
                            database.collection("Recensione").whereEqualTo("struttura",id_struttura)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Log.i("Recension", "Tutto ok");
                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    String voto= String.valueOf(document.get("voto"));
                                                    voti=Double.parseDouble(voto);
                                                    totale+=voti;
                                                    count++;




                                                }
                                                media=totale/count;
                                                notebookRef.document(id_struttura).update("valutazione",media);

                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Context, "Errore", Toast.LENGTH_SHORT).show();
                            Log.w("Recensione", "Error adding document", e);
                        }
                    });

        }


    }


    private boolean recensione_gia_scritta(final String user_id) {

        flag  = true;
        database.collection("Recensione").whereEqualTo("idAutore",user_id).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for( DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                                if(document.getString("struttura").equals(id_struttura)){
                                    flag = false;
                                    break;
                                }
                            }

                        }
                    }
                });
        return flag;
    }


    private boolean IsValidRank(String voto) {
        if(voto.length() == 0){
            Toast.makeText(Context, "Inserire voto recensione", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean IsValidText(String testo) {
        if(testo.length() == 0){
            Toast.makeText(Context, "Inserire testo recensione", Toast.LENGTH_SHORT).show();
            return false;
        }else if(testo.length() < 10){
            Toast.makeText(Context, "Il testo deve essere di almeno 10 caratteri", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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



    //Metodi per Activity_visualizza_recensioni_struttura
    //TODO PROVARE DOPO AVER PULITO IL DATABASE
    public void configurazione_lista_recensioni(RecyclerView lista_recensioni,Button segnalazione) {
        recensioni = database.collection("Recensione").whereEqualTo("struttura",id_struttura);
        segnala = segnalazione;
        FirestoreRecyclerOptions<Recensioni> options = new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(recensioni,Recensioni.class).build();
        adapter = new RecensioniStrutturaAdapter(options);


        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
        lista_recensioni.setAdapter(adapter);

        adapter.setOnItemClickListner(new RecensioniStrutturaAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                idAutore = docSnapshot.getString("idAutore");
                recensione_selezionata = docSnapshot.getId();
                if(segnala.getVisibility() != View.VISIBLE){
                    segnala.setVisibility(View.VISIBLE);
                }
                Toast.makeText(Context,"Recensione selezionata",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mostra_recensioni() {
        adapter.startListening();
    }

    public void togli_recensioni() {
        adapter.stopListening();
    }


    public void ricerca_per_categoria(String tipo_struttura) {
        Intent Ricerca = new Intent(Context, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura",tipo_struttura);
        Ricerca.putExtra("Tipo ricerca","Category button");
        Context.startActivity(Ricerca);
    }

    public void filtra_recensioni(RecyclerView lista_recensioni, String voto) {
        Query filtro = recensioni.whereEqualTo("voto",Integer.parseInt(voto));

        FirestoreRecyclerOptions<Recensioni> options = new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(filtro,Recensioni.class).build();
        adapter.stopListening();
        adapter = new RecensioniStrutturaAdapter(options);
        adapter.setOnItemClickListner(new RecensioniStrutturaAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                idAutore = docSnapshot.getString("idAutore");
                recensione_selezionata = docSnapshot.getId();
                if(segnala.getVisibility() != View.VISIBLE){
                    segnala.setVisibility(View.VISIBLE);
                }
                Toast.makeText(Context,"Recensione selezionata",Toast.LENGTH_SHORT).show();
            }
        });

        lista_recensioni.setAdapter(adapter);

        adapter.startListening();
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


    //TODO PROVARE DOPO AVER PULITO IL DATABASE
    public void segnala_recensione() {

        final DocumentReference datiutente = database.collection("Utenti").document(idAutore);
        final DocumentReference recensione = database.collection("Recensione").document(recensione_selezionata);
        getTesto(recensione);

        datiutente.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot){
                if (documentSnapshot.exists()) {
                    final Map<String, Object> segnalazione = new HashMap<>();
                    segnalazione.put("nickname", documentSnapshot.getString("nickname"));
                    segnalazione.put("struttura", id_struttura);
                    segnalazione.put("testo",testo);
                    segnalazione.put("recensione",recensione_selezionata);
                    database.collection("Segnalazioni").add(segnalazione).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Context, "Segnalazione Inviata", Toast.LENGTH_SHORT).show();
                            Log.d("Segnalazioni", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Context, "Errore", Toast.LENGTH_SHORT).show();
                            Log.w("Segnalazioni", "Error adding document", e);
                        }
                    });

                }else{
                    Toast.makeText(Context, "Errore", Toast.LENGTH_SHORT).show();
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
        testo = null;
        recensione_selezionata = null;
    }

    public void setSpinner(Spinner filtro_voto) {
        ArrayAdapter<CharSequence> adapter_recensioni = ArrayAdapter.createFromResource(Context, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        filtro_voto.setAdapter(adapter_recensioni);
    }
}
