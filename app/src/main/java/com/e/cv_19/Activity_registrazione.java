package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_registrazione extends AppCompatActivity {
    private EditText campo_nickname;
    private EditText campo_nome;
    private EditText campo_cognome;
    private EditText campo_email;
    private EditText campo_password;
    private EditText campo_ripeti_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        campo_nickname = findViewById(R.id.editTextNickname);
        campo_nome = findViewById(R.id.editTextNome);
        campo_cognome = findViewById(R.id.editTextCognome);
        campo_email = findViewById(R.id.editTextEmail);
        campo_password = findViewById(R.id.editTextpassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiPassword);
    }

    public void Conferma_is_clicked(View view) {
    }

    public void Annulla_is_clicked(View view) {
    }
}
