package com.e.cv_19.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerLogin;
import com.e.cv_19.R;

public class Activity_recupera_password extends AppCompatActivity {

    private EditText campo_email;
    private ControllerLogin Controller = new ControllerLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_password);
        campo_email = findViewById(R.id.editTextEmail);
    }

    public void Invia_email_is_clicked(View view) {
        String email = campo_email.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Inserire mail", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@")) {
            Toast.makeText(getApplicationContext(), "Mail non valida", Toast.LENGTH_SHORT).show();
        } else {
            Controller.invia_email_recupero_password(email,Activity_recupera_password.this);
        }

    }

    public void Annulla_is_clicked(View view) {
        finish();
    }




}
