package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_modifica_impostazioni extends AppCompatActivity {

    private EditText campo_email;
    private EditText campo_vecchia_password;
    private EditText campo_nuova_password;
    private EditText campo_ripeti_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_impostazioni);

        campo_email = findViewById(R.id.Editviewemail);
        campo_vecchia_password = findViewById(R.id.editTextVecchiaPassword);
        campo_nuova_password = findViewById(R.id.editTextNuovaPassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiPassword);
    }

    public void Annulla_is_clicked(View view) {
    }

    public void Conferma_is_clicked(View view) {
    }

    public void Invia_richiesta_cancellazione_is_clicked(View view) {
    }
}
