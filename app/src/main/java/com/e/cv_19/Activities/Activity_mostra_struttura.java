package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Activity_mostra_struttura extends AppCompatActivity {

    private EditText campo_ricerca;
    private EditText testo_recensione;
    private TextView nome_struttura;
    private TextView descrizione_struttura;
    private ImageView immagine_struttura;
    private Spinner voto_recensione;
    private String id_struttura;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_struttura);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        testo_recensione= findViewById(R.id.editTextTestoRecensione);
        voto_recensione = findViewById(R.id.spinnerVoto);
        immagine_struttura = findViewById(R.id.imageStruttura);
        nome_struttura = findViewById(R.id.textViewNomeStruttura);
        descrizione_struttura = findViewById(R.id.textViewDescrizione);
        ArrayAdapter<CharSequence> adapter_voto = ArrayAdapter.createFromResource(this, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        voto_recensione.setAdapter(adapter_voto);

        Bundle dati_ricevuti = getIntent().getExtras();
        id_struttura = dati_ricevuti.getString("id");
        getInfoStruttura();

    }

    private void getInfoStruttura(){
        final DocumentReference datiStruttura = database.collection("Strutture").document(id_struttura);

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



    public void Visualizza_recensioni_struttura(View view) {
    }

    public void pubblica_recensione(View view) {
        String testo = testo_recensione.getText().toString();
        String voto = voto_recensione.getSelectedItem().toString();
        if(IsValidText(testo) && IsValidRank(voto)){

            FirebaseUser currentUser = mAuth.getCurrentUser();
            String usid= currentUser.getUid();

            Map<String,Object> nuova_recensione = new HashMap<>();
            nuova_recensione.put("testo",testo);
            nuova_recensione.put("voto",voto);
            nuova_recensione.put("idcliente",usid);
            nuova_recensione.put("struttura",id_struttura);



            database.collection("Recensione").add(nuova_recensione)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Recensione pubblicata", Toast.LENGTH_SHORT).show();
                            Log.d("Recensione", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                            Log.w("Recensione", "Error adding document", e);
                        }
                    });
        }

    }

    private boolean IsValidRank(String voto) {
        if(voto.length() == 0){
            Toast.makeText(this, "Inserire voto recensione", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean IsValidText(String testo) {
        if(testo.length() == 0){
            Toast.makeText(this, "Inserire testo recensione", Toast.LENGTH_SHORT).show();
            return false;
        }else if(testo.length() < 10){
            Toast.makeText(this, "Il testo deve essere di almeno 10 caratteri", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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


    public void click_on_menù(View view) {
        Intent menù = new Intent(this ,Activity_menu.class);
        startActivity(menù);
    }
}
