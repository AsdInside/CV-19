package com.e.cv_19.Controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.e.cv_19.Activities.Main_Activity;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

public class ControllerMain {
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = database.collection("Strutture");
    private StruttureAdapter adapter;
    private RecensioniAdapter adapter_recensioni;


    public ControllerMain(){}

    //Metodi per Activity_menù

    public void effettua_logout(Activity_menu activity_menu){
        Intent login =new Intent(activity_menu, Activity_login.class);
        FirebaseAuth.getInstance().signOut();
        activity_menu.finish();
        activity_menu.startActivity(login);
    }


    public void ricerca_avanzata(Activity_menu activity_menu){
        Intent intent_ricerca = new Intent(activity_menu, Activity_ricerca.class);
        activity_menu.startActivity(intent_ricerca);
    }

    public void mostra_recensioni_personali(Activity_menu activity_menu){
        Intent intent_recensioni = new Intent(activity_menu, Activity_visualizza_recensioni.class);
        activity_menu.startActivity(intent_recensioni);

    }

    public void mostra_impostazioni(Activity_menu activity_menu){
        Intent intent_impostazioni = new Intent(activity_menu, Activity_modifica_impostazioni.class);
        activity_menu.startActivity(intent_impostazioni);

    }


    //Metodi per Main_Activity

    public void ricerca_per_nome(final String nome,final Main_Activity main_activity){
        notebookRef.orderBy("valutazione",Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                            if(document.getString("nome").equalsIgnoreCase(nome)){
                                Struttura_selezionata(document.getId(),main_activity);

                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(main_activity,"nessuna struttura trovata",Toast.LENGTH_SHORT).show();
                }
            });

    }




    public void visualizza_mappa(Main_Activity main_activity){
        Intent Mappa = new Intent(main_activity, Activity_visualizza_mappa.class);
        main_activity.startActivity(Mappa);
    }

    public void ricerca_per_categoria(Main_Activity main_activity,String tipo_struttura){
        Intent Ricerca = new Intent(main_activity, Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura",tipo_struttura);
        Ricerca.putExtra("Tipo ricerca","Category button");
        main_activity.startActivity(Ricerca);

    }

    public void mostra_menù(Main_Activity main_activity){
        Intent Intent_menù = new Intent(main_activity, Activity_menu.class);
        main_activity.startActivity(Intent_menù);
    }

    public void mostra_strutture(RecyclerView lista_strutture,final Main_Activity main_activity){
        Query ordinamento = notebookRef.orderBy("valutazione",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(ordinamento,Strutture.class).build();
        adapter = new StruttureAdapter(options);

        adapter.setOnItemClickListner(new StruttureAdapter.OnItemClickListner(){
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                String id_struttura = docSnapshot.getId();
                Struttura_selezionata(id_struttura,main_activity);
            }
        });

        lista_strutture.setHasFixedSize(false);
        lista_strutture.setLayoutManager(new LinearLayoutManager(main_activity));
        lista_strutture.setAdapter(adapter);

    }

    private void Struttura_selezionata(String id_struttura, Main_Activity main_activity) {
        Intent mostra_struttura = new Intent(main_activity, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        main_activity.startActivity(mostra_struttura);
    }

    public void mostra_lista(){
        adapter.startListening();
    }

    public void togli_lista(){
        adapter.stopListening();
    }

    //Metodi per Activity_mostra_recensioni

    public void mostra_recensioni_personali(RecyclerView lista_recensioni,final Activity_visualizza_recensioni main_activity){
        CollectionReference Recensioni = database.collection("Recensione");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser utente = mAuth.getCurrentUser();
        Query recensioni_utente = Recensioni.whereEqualTo("idAutore",utente.getUid());
        FirestoreRecyclerOptions<Recensioni> options= new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(recensioni_utente, Recensioni.class).build();

        adapter_recensioni = new RecensioniAdapter(options);

        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(main_activity));
        lista_recensioni.setAdapter(adapter_recensioni);
    }

    public void mostra_recensioni(){
        adapter_recensioni.startListening();
    }

    public void togli_recensioni(){
        adapter_recensioni.stopListening();
    }

    //Metodi per Activity_modifica_impostazioni

    public void richiesta_cancellazione(Activity_modifica_impostazioni activity_modifica_impostazioni){
        Intent cancellazione = new Intent(activity_modifica_impostazioni,Activity_richiesta_cancellazione.class);
        activity_modifica_impostazioni.startActivity(cancellazione);

    }

    public void modifica_password(final String vecchia_password,final String nuova_password,final Activity_modifica_impostazioni activity_modifica_impostazioni){
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
                                        Toast.makeText(activity_modifica_impostazioni, "Password aggiornata",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("password", "Error password not updated");
                                        Toast.makeText(activity_modifica_impostazioni, "Impossibile aggiornare",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(activity_modifica_impostazioni,"Password non corretta",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("password", "Error auth failed");
                        }

                    }
                });
        batch.commit();

    }




}
