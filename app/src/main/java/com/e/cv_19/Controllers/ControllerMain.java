package com.e.cv_19.Controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Activities.Activity_login;
import com.e.cv_19.Activities.Activity_menu;
import com.e.cv_19.Activities.Activity_modifica_impostazioni;
import com.e.cv_19.Activities.Activity_mostra_struttura;
import com.e.cv_19.Activities.Activity_ricerca;
import com.e.cv_19.Activities.Activity_richiesta_cancellazione;
import com.e.cv_19.Activities.Activity_risultati_ricerca;
import com.e.cv_19.Activities.Activity_visualizza_mappa;
import com.e.cv_19.Activities.Activity_visualizza_recensioni;
import com.e.cv_19.Adapter.RecensioniAdapter;
import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Recensioni;
import com.e.cv_19.Model.Strutture;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class ControllerMain {
    private AppCompatActivity Context;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private StruttureAdapter adapter;
    private RecensioniAdapter adapter_recensioni;


    public ControllerMain(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodi per Activity_menù

    public void effettua_logout(){
        Intent login =new Intent(Context, Activity_login.class);
        FirebaseAuth.getInstance().signOut();
        Context.finish();
        Context.startActivity(login);
    }


    public void ricerca_avanzata(){
        Intent intent_ricerca = new Intent(Context, Activity_ricerca.class);
        Context.startActivity(intent_ricerca);
    }

    public void mostra_recensioni_personali(){
        Intent intent_recensioni = new Intent(Context, Activity_visualizza_recensioni.class);
        Context.startActivity(intent_recensioni);

    }

    public void mostra_impostazioni(){
        Intent intent_impostazioni = new Intent(Context, Activity_modifica_impostazioni.class);
        Context.startActivity(intent_impostazioni);

    }


    //Metodi per Main_Activity

    public void ricerca_per_nome(final String nome){
        notebookRef.orderBy("valutazione",Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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




    public void visualizza_mappa(){
        Intent Mappa = new Intent(Context, Activity_visualizza_mappa.class);
        Context.startActivity(Mappa);
    }

    public void ricerca_per_categoria(String tipo_struttura){
        Intent Ricerca = new Intent(Context, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura",tipo_struttura);
        Ricerca.putExtra("Tipo ricerca","Category button");
        Context.startActivity(Ricerca);

    }

    public void mostra_menù(){
        Intent Intent_menù = new Intent(Context, Activity_menu.class);
        Context.startActivity(Intent_menù);
    }

    public void mostra_strutture(RecyclerView lista_strutture){
        Query ordinamento = notebookRef.orderBy("valutazione",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(ordinamento,Strutture.class).build();
        adapter = new StruttureAdapter(options);

        adapter.setOnItemClickListner(new StruttureAdapter.OnItemClickListner(){
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Struttura_selezionata(id_struttura);
            }
        });

        lista_strutture.setHasFixedSize(false);
        lista_strutture.setLayoutManager(new LinearLayoutManager(Context));
        lista_strutture.setAdapter(adapter);

    }

    private void Struttura_selezionata(String id_struttura) {
        Intent mostra_struttura = new Intent(Context, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        Context.startActivity(mostra_struttura);
    }

    public void mostra_lista(){
        adapter.startListening();
    }

    public void togli_lista(){
        adapter.stopListening();
    }

    //Metodi per Activity_visualizza_recensioni

    public void mostra_recensioni_personali(RecyclerView lista_recensioni){
        CollectionReference Recensioni = database.collection("Recensione");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser utente = mAuth.getCurrentUser();
        Query recensioni_utente = Recensioni.whereEqualTo("idAutore",utente.getUid());
        FirestoreRecyclerOptions<Recensioni> options= new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(recensioni_utente, Recensioni.class).build();

        adapter_recensioni = new RecensioniAdapter(options);

        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(Context));
        lista_recensioni.setAdapter(adapter_recensioni);
    }

    public void mostra_recensioni(){
        adapter_recensioni.startListening();
    }

    public void togli_recensioni(){
        adapter_recensioni.stopListening();
    }

    //Metodi per Activity_modifica_impostazioni

    public void richiesta_cancellazione(){
        Intent cancellazione = new Intent(Context,Activity_richiesta_cancellazione.class);
        Context.startActivity(cancellazione);

    }

    public void modifica_password(final String vecchia_password,final String nuova_password){
        FirebaseUser utente = FirebaseAuth.getInstance().getCurrentUser();
        WriteBatch batch = database.batch();
        AuthCredential credenziali_di_accesso = EmailAuthProvider.getCredential(utente.getEmail(), vecchia_password);

        utente.reauthenticate(credenziali_di_accesso).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updatePassword(nuova_password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("password", "Password updated");
                                        Toast.makeText(Context, "Password aggiornata",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("password", "Error password not updated");
                                        Toast.makeText(Context, "Impossibile aggiornare",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Context,"Password non corretta",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("password", "Error auth failed");
                        }

                    }
                });
        batch.commit();

    }

    //Metodo per Activity_richiesta_cancellazione

    public void invia_richiesta_cancellazione(final String motivazione) {
        if(motivazione.isEmpty()){
            Toast.makeText(Context, "Inserire una motivazione", Toast.LENGTH_SHORT).show();
        }else if(motivazione.length() < 10){
            Toast.makeText(Context, "La motivazione deve essere di almeno 10 caratteri", Toast.LENGTH_SHORT).show();
        }
        final FirebaseUser utente = FirebaseAuth.getInstance().getCurrentUser();
        String id_utente= utente.getUid();
        final DocumentReference datiutente = database.collection("Utenti").document(id_utente);

        datiutente.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot){
                if (documentSnapshot.exists()) {
                    String  nickname= documentSnapshot.getString("nickname");
                    final Map<String, Object> obj = new HashMap<>();
                    obj.put("motivazione", motivazione);
                    obj.put("email", utente.getEmail());
                    obj.put("nickname", nickname);

                    database.collection("Cancellazioni")
                            .add(obj)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Context, "Richiesta Inviata", Toast.LENGTH_SHORT).show();
                                    Log.d("Cancellazioni", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Context, "Errore", Toast.LENGTH_SHORT).show();
                                    Log.w("Cancellazioni", "Error adding document", e);
                                }
                            });
                }
            }
        });
    }


}
