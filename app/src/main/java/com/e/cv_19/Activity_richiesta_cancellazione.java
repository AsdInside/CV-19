package com.e.cv_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity_richiesta_cancellazione extends AppCompatActivity {

    private EditText motivazione_cancellazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richiesta_cancellazione);

        motivazione_cancellazione = findViewById(R.id.editTextMotivazione);
    }

    public void click_on_annulla(View view) {
        finish();
    }

    public void click_on_conferma(View view) {
    }
}
