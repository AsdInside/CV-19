package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerLogin;
import com.e.cv_19.R;

public class Activity_registrazione extends AppCompatActivity {
    private EditText campo_nickname;
    private EditText campo_nome;
    private EditText campo_cognome;
    private EditText campo_email;
    private EditText campo_password;
    private EditText campo_ripeti_password;
    private ControllerLogin Controller = new ControllerLogin();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        campo_nickname = findViewById(R.id.editTextNickname);
        campo_nome = findViewById(R.id.editTextNome);
        campo_cognome = findViewById(R.id.editTextCognome);
        campo_email = findViewById(R.id.editTextEmail);
        campo_password = findViewById(R.id.editTextPassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiPassword);
    }

    public void Conferma_is_clicked(View view) {
        String nickname = campo_nickname.getText().toString();
        String nome = campo_nome.getText().toString();
        String cognome = campo_cognome.getText().toString();
        String email = campo_email.getText().toString();
        String password = campo_password.getText().toString();
        String ripeti_password = campo_ripeti_password.getText().toString();

        if (!Controller.isValidName(nickname))
            Toast.makeText(getApplicationContext(), "Inserire nickname", Toast.LENGTH_SHORT).show();
        else if (!Controller.isValidName(nome)) {
            Toast.makeText(getApplicationContext(), "Inserire nome", Toast.LENGTH_SHORT).show();
        } else if (!Controller.isValidName(cognome)) {
            Toast.makeText(getApplicationContext(), "Inserire cognome", Toast.LENGTH_SHORT).show();
        } else if (!Controller.isValidMail(email)) {
            Toast.makeText(getApplicationContext(), "Inserire una mail valida", Toast.LENGTH_SHORT).show();
        } else if (!Controller.isValidPassword(password, ripeti_password)) {
            Toast.makeText(getApplicationContext(), "Password non coincidente o inferiore a 5 caratteri", Toast.LENGTH_SHORT).show();
        } else {
            Controller.createUser(email, password,nome,cognome,nickname,Activity_registrazione.this);
        }

    }

    public void Annulla_is_clicked(View view) {
        finish();
    }

}