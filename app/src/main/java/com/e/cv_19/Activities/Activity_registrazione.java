package com.e.cv_19.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
public class Activity_registrazione extends AppCompatActivity {
    private EditText campo_nickname;
    private EditText campo_nome;
    private EditText campo_cognome;
    private EditText campo_email;
    private EditText campo_password;
    private EditText campo_ripeti_password;
    private FirebaseAuth mAuth;
    private final String TAG = "Register Activity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        campo_nickname = findViewById(R.id.editTextNickname);
        campo_nome = findViewById(R.id.editTextNome);
        campo_cognome = findViewById(R.id.editTextCognome);
        campo_email = findViewById(R.id.editTextEmail);
        campo_password = findViewById(R.id.editTextPassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiPassword);
    }


    private void createUser(String email, String password) {
        final String nome = campo_nome.getText().toString();
        final String cognome = campo_cognome.getText().toString();
        final String nickname = campo_nickname.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Activity_registrazione.this, "Registrazione avvenuta con successo",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            addFirestore(nickname, nome, cognome);
                            finish();

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


    public void Conferma_is_clicked(View view) {
        //validazione dati utente
        String nickname = campo_nickname.getText().toString();
        String nome = campo_nome.getText().toString();
        String cognome = campo_cognome.getText().toString();
        String email = campo_email.getText().toString();
        String password = campo_password.getText().toString();
        String ripeti_password = campo_ripeti_password.getText().toString();

        if (!isValidName(nickname))
            Toast.makeText(getApplicationContext(), "Inserire nickname", Toast.LENGTH_SHORT).show();
        else if (!isValidName(nome)) {
            Toast.makeText(getApplicationContext(), "Inserire nome", Toast.LENGTH_SHORT).show();
        } else if (!isValidName(cognome)) {
            Toast.makeText(getApplicationContext(), "Inserire cognome", Toast.LENGTH_SHORT).show();
        } else if (!isValidMail(email)) {
            Toast.makeText(getApplicationContext(), "Inserire una mail valida", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password, ripeti_password)) {
            Toast.makeText(getApplicationContext(), "Password non coincidente o inferiore a 5 caratteri", Toast.LENGTH_SHORT).show();
        } else {
            createUser(email, password);
        }
    }

    public void Annulla_is_clicked(View view) {
        finish();
    }


    private boolean isValidName(String name) {
        return name.length() > 0;
    }


    private boolean isValidMail(String email) {
        return email.contains("@");
    }


    private boolean isValidPassword(String password, String ripeti_password) {
        return password.equals(ripeti_password) && password.length() > 4;
    }

    public void addFirestore(String nickname, String nome, String cognome) {

        FirebaseUser userZ = mAuth.getCurrentUser();

        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("nome", nome);
        user.put("cognome", cognome);
        user.put("nickname", nickname);
        user.put("idUtente", userZ.getUid());


        // Add a new document with a generated ID
        db.collection("Utenti").document(userZ.getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                  //      Toast.makeText(this,"Registrazione avvenuta con successo").show();
                    }
                });

    }
}