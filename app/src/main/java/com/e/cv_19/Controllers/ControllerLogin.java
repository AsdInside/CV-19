package com.e.cv_19.Controllers;


import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.e.cv_19.Activities.Activity_login;
import com.e.cv_19.Activities.Activity_recupera_password;
import com.e.cv_19.Activities.Activity_registrazione;
import com.e.cv_19.Activities.Main_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;


public class ControllerLogin {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore database = FirebaseFirestore.getInstance();


    public ControllerLogin(){}

    public void createUser(final String email, final String password, final String nome, final String cognome, final String nickname,final Activity_registrazione activity_registrazione) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_registrazione, "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
                            addFirestore(nickname, nome, cognome);
                            Intent main_activity = new Intent(activity_registrazione, Main_Activity.class);
                            activity_registrazione.startActivity(main_activity);
                            activity_registrazione.finish();
                        } else {
                            Toast.makeText(activity_registrazione, "Registratione fallita.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void addFirestore(String nickname, String nome, String cognome) {

        FirebaseUser Id_utente = mAuth.getCurrentUser();

        Map<String, Object> nouvo_utente = new HashMap<>();
        nouvo_utente.put("nome", nome);
        nouvo_utente.put("cognome", cognome);
        nouvo_utente.put("nickname", nickname);
        nouvo_utente.put("idUtente", Id_utente.getUid());

        database.collection("Utenti").document(Id_utente.getUid()).set(nouvo_utente);

    }



    public boolean isValidName(String name) {
        return name.length() > 0;
    }


    public boolean isValidMail(String email) { return email.contains("@"); }


    public boolean isValidPassword(String password, String ripeti_password) {
        return password.equals(ripeti_password) && password.length() > 4;
    }

    public void invia_email_recupero_password(String email,final Activity_recupera_password activity_recupera_password){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_recupera_password, "Mail di recupero inviata", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(activity_recupera_password, "Impossibile inviare mail di recupero", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public boolean isValidPassword(String password) {
        return password.length() != 0;
    }

    public void Effettua_login(String email, String password,final Activity_login activity_login)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Auto_Login(activity_login);
                        } else {
                            Toast.makeText(activity_login, "Autenticazione fallita.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void Auto_Login(Activity_login activity_login) {

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        database.setFirestoreSettings(settings);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Intent main_activity = new Intent(activity_login,Main_Activity.class);
            activity_login.startActivity(main_activity);

        }

    }

    public void Mostra_activity_registrazione(Activity_login activity_login){
        Intent activity_registrazione = new Intent(activity_login,Activity_registrazione.class);
        activity_login.startActivity(activity_registrazione);

    }

    public void Mostra_activity_recupero_password(Activity_login activity_login){
        Intent activity_recupera_password = new Intent(activity_login,Activity_recupera_password.class);
        activity_login.startActivity(activity_recupera_password);
    }


}
