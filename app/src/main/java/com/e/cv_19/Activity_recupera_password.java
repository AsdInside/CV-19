package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_recupera_password extends AppCompatActivity {

    private EditText campo_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_password);

        campo_email = findViewById(R.id.editTextEmail);
    }

    public void Invia_email_is_clicked(View view) {

        /*per ottenere i dati dalla EditText usare getText().toString() su campo_email*/
    }

    public void Annulla_is_clicked(View view) {
    }
}
