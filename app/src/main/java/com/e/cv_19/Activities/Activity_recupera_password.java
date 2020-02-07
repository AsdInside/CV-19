package com.e.cv_19.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_recupera_password extends AppCompatActivity {

    private EditText campo_email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_password);
        auth = FirebaseAuth.getInstance();
        campo_email = findViewById(R.id.editTextEmail);
    }

    public void Invia_email_is_clicked(View view) {
        String email = campo_email.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Inserire mail", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@")) {
            Toast.makeText(getApplicationContext(), "Mail non valida", Toast.LENGTH_SHORT).show();
        } else {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Mail di recupero inviata", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Impossibile inviare mail di recupero", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void Annulla_is_clicked(View view) {
        finish();
    }




}
