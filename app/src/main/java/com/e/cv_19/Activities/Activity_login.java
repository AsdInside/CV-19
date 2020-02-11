package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


public class Activity_login extends AppCompatActivity {

    private static final String TAG = "Activity Login";
    private FirebaseAuth mAuth;
    private EditText campo_email;
    private EditText campo_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        campo_email = findViewById(R.id.Editviewemail);
        campo_password = findViewById(R.id.editTextpassword);
       updateUI();
    }


    @Override
    public void onStart() {
        super.onStart();



    }

    private void updateUI() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            String email = user.getEmail();
            Intent intent = new Intent(this,Main_Activity.class);
            intent.putExtra("msg",email);
            startActivity(intent);

        }

    }



    public void Login_is_clicked(View view) {



        String username=campo_email.getText().toString();
        String password=campo_password.getText().toString();


        if (!isValidMail(username)) {
            Toast.makeText(getApplicationContext(), "Inserire una mail valida", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(getApplicationContext(), "La password deve avere almeno 6 caratteri", Toast.LENGTH_SHORT).show();
        } else {
            loginUser(username,password);
        }


    }

    private void loginUser(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Activity_login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }
    public void Registrati_is_clicked(View view) {
        Intent Intent_registrazione = new Intent(this,Activity_registrazione.class);
        startActivity(Intent_registrazione);
    }

    public void Recupera_is_clicked(View view) {
        Intent Intent_recupera = new Intent(this,Activity_recupera_password.class);
        startActivity(Intent_recupera);
    }


    private boolean isValidPassword(String password) {
        String conferma_password=campo_password.getText().toString();
        return conferma_password.equals(password) && password.length()>5;

    }

    private boolean isValidMail(String email){ return email.contains("@"); }

}
