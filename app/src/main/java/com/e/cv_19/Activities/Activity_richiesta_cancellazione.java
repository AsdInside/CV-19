package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerMain;
import com.e.cv_19.R;



public class Activity_richiesta_cancellazione extends AppCompatActivity {

    private EditText motivazione_cancellazione;
    private ControllerMain Controller = new ControllerMain(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richiesta_cancellazione);

        motivazione_cancellazione = findViewById(R.id.editTextMotivazione);
    }

    public void click_on_annulla(View view) {
        finish();
    }



    public void click_on_conferma(View view) { Controller.invia_richiesta_cancellazione(motivazione_cancellazione.getText().toString()); }


}