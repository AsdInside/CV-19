package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
//TODO FUNZIONALITA' DA TESTARE!
public class Activity_modifica_impostazioni extends AppCompatActivity {

    private EditText campo_email;
    private EditText campo_vecchia_password;
    private EditText campo_nuova_password;
    private EditText campo_ripeti_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_impostazioni);

        campo_email = findViewById(R.id.editTextEmail);
        campo_vecchia_password = findViewById(R.id.editTextVecchiaPassword);
        campo_nuova_password = findViewById(R.id.editTextNuovaPassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiNuovaPassword);
    }

    public void Annulla_is_clicked(View view) {
        finish();
    }

    public void Conferma_is_clicked(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch=db.batch();
        String tmp=campo_vecchia_password.getText().toString();
        if(!TextUtils.isEmpty(tmp)){
            String tmp2=campo_nuova_password.getText().toString();
            if(!TextUtils.isEmpty(tmp2)){
                String tmp3=campo_ripeti_password.getText().toString();
                if(!TextUtils.isEmpty(tmp3)){
                    if(tmp3.equals(tmp2)){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        AuthCredential credential = EmailAuthProvider
                                .getCredential(user.getEmail(), tmp);

                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String tmp2=campo_nuova_password.getText().toString();
                                            user.updatePassword(tmp2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("password", "Password updated");
                                                        Toast.makeText(getApplicationContext(), "Password aggiornata",
                                                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Log.d("password", "Error password not updated");
                                                        Toast.makeText(getApplicationContext(), "Impossibile aggiornare",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Password non corretta",
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("password", "Error auth failed");
                                        }
                                    }
                                });
                    }else
                        Toast.makeText(this, "Le password non coincidono",
                                Toast.LENGTH_SHORT).show();
                }
            }
        }
        batch.commit();


    }

    public void Invia_richiesta_cancellazione_is_clicked(View view) {

        Intent cancellazione = new Intent(this,Activity_richiesta_cancellazione.class);

        startActivity(cancellazione);
    }
}
