package com.e.cv_19;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_registrazione extends AppCompatActivity {
    private EditText campo_nickname;
    private EditText campo_nome;
    private EditText campo_cognome;
    private EditText campo_email;
    private EditText campo_password;
    private EditText campo_ripeti_password;
    private FirebaseAuth mAuth;
    private final String TAG="Register Activity";

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        mAuth = FirebaseAuth.getInstance();

        campo_nome = findViewById(R.id.editTextNome);
        campo_cognome = findViewById(R.id.editTextCognome);
        campo_email = findViewById(R.id.editTextEmail);
        campo_password = findViewById(R.id.editTextpassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiPassword);
    }

    private void createUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Activity_registrazione.this, "Registrazione avvenuta con successo",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Activity_registrazione.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }

    //TODO PROVANDO L'APP E CLICCANDO SU CONFERMA MI TORNA SU LOGIN ACTIVITY :/

    public void Conferma_is_clicked(View view) {
        //validazione dati utente
        String nome = campo_nome.getText().toString();
        String email = campo_email.getText().toString();
        String password = campo_password.getText().toString();

        if (!isValidName(nome))
            Toast.makeText(getApplicationContext(), "Inserire almeno 5 caratteri", Toast.LENGTH_LONG).show();
        else if (!isValidMail(email)) {
            Toast.makeText(getApplicationContext(), "Inserire una mail valida", Toast.LENGTH_LONG).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(getApplicationContext(), "La password deve avere almeno 5 caratteri", Toast.LENGTH_LONG).show();
        } else {
            createUser(email,password);
        }
    }

    public void Annulla_is_clicked(View view) {
        Intent intent1 = new Intent(getApplicationContext(),Activity_login.class);
        startActivity(intent1);
    }


    private boolean isValidName(String name) {

            if(name.length()>5)
                return true;
            else
                return false;

    }


    private boolean isValidMail(String email){

        return email.contains("@");
    }


    private boolean isValidPassword(String password){

        String confermapassword=campo_password.getText().toString();
        return confermapassword.equals(password) && password.length()>5;
    }

}