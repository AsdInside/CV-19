package com.e.cv_19.Controllers;


import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private AppCompatActivity Context;


    public ControllerLogin(AppCompatActivity Activity){
        Context = Activity;
    }

    //Metodi per Activity_registrazione

    public void createUser(final String email, final String password, final String nome, final String cognome, final String nickname) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Context, "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
                            addFirestore(nickname, nome, cognome);
                            Intent main_activity = new Intent(Context, Main_Activity.class);
                            Context.finish();
                            Context.startActivity(main_activity);

                        } else {
                            Toast.makeText(Context, "Registratione fallita.", Toast.LENGTH_SHORT).show();
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

    //Metodi per il controllo degli input

    public boolean isValidPassword(String password) {
        return password.length() != 0;
    }

    public boolean isValidName(String name) { return name.length() > 0; }

    public boolean isValidMail(String email) { return email.contains("@"); }

    public boolean isValidPassword(String password, String ripeti_password) { return password.equals(ripeti_password) && password.length() > 5; }

    //Metodo per Activity_recupera_password

    public void invia_email_recupero_password(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Context, "Mail di recupero inviata", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Context, "Impossibile inviare mail di recupero", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //Metodi per Activity_login

    public void Effettua_login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Auto_Login();
                        } else {
                            Toast.makeText(Context, "Autenticazione fallita.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void Auto_Login() {

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        database.setFirestoreSettings(settings);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Intent main_activity = new Intent(Context,Main_Activity.class);
            Context.startActivity(main_activity);

        }

    }


    public void Mostra_activity_registrazione(){
        Intent activity_registrazione = new Intent(Context,Activity_registrazione.class);
        Context.startActivity(activity_registrazione);

    }

    public void Mostra_activity_recupero_password(){
        Intent activity_recupera_password = new Intent(Context,Activity_recupera_password.class);
        Context.startActivity(activity_recupera_password);
    }


}
