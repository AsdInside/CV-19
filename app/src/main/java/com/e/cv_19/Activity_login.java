package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Activity_login extends AppCompatActivity {
    private EditText campo_email;
    private EditText campo_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campo_email = findViewById(R.id.Editviewemail);
        campo_password = findViewById(R.id.editTextpassword);
    }

    public void Login_is_clicked(View view) {

        /*per ottenere i dati dalle EditText usare getText().toString() su campo_email e password*/

    }

    public void Registrati_is_clicked(View view) {

    }

    public void Recupera_is_clicked(View view) {

    }

}
