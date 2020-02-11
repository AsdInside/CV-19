package com.e.cv_19.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity_richiesta_cancellazione extends AppCompatActivity {

    private EditText motivazione_cancellazione;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richiesta_cancellazione);

        motivazione_cancellazione = findViewById(R.id.editTextMotivazione);
    }

    public void click_on_annulla(View view) {
        finish();
    }



    public void click_on_conferma(View view) {

        final String motivazione = motivazione_cancellazione.getText().toString();

        if (TextUtils.isEmpty(motivazione)) {
            Toast.makeText(getApplicationContext(), "Inserire una motivazione", Toast.LENGTH_SHORT).show();
        } else if (motivazione.length() < 10)
            Toast.makeText(getApplicationContext(), "Inserire almeno 10 caratteri", Toast.LENGTH_SHORT).show();
        else {
            String usid= user.getUid();
            final DocumentReference datiutente = db.collection("Utenti").document(usid);

            datiutente.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot){
                    if (documentSnapshot.exists()) {
                        String  nickname= documentSnapshot.getString("nickname");
                        final Map<String, Object> obj = new HashMap<>();
                        obj.put("motivazione", motivazione);
                        obj.put("email", user.getEmail());
                        obj.put("nickname", nickname);

                        db.collection("Cancellazioni")
                                .add(obj)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(), "Richiesta Inviata", Toast.LENGTH_SHORT).show();
                                        Log.d("Cancellazioni", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                                        Log.w("Cancellazioni", "Error adding document", e);
                                    }
                                });
                    }
                }
            });




        }


    }






}