package com.e.cv_19.Activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerMain;
import com.e.cv_19.R;

public class Activity_modifica_impostazioni extends AppCompatActivity {

    private EditText campo_email;
    private EditText campo_vecchia_password;
    private EditText campo_nuova_password;
    private EditText campo_ripeti_password;
    private ControllerMain Controller = new ControllerMain();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_impostazioni);

        campo_email = findViewById(R.id.editTextEmail);
        campo_vecchia_password = findViewById(R.id.editTextVecchiaPassword);
        campo_nuova_password = findViewById(R.id.editTextNuovaPassword);
        campo_ripeti_password = findViewById(R.id.editTextRipetiNuovaPassword);
    }

    public void Annulla_is_clicked(View view) {
        finish();
    }

    public void Conferma_is_clicked(View view) {

        String vecchia_password = campo_vecchia_password.getText().toString();
        if(!TextUtils.isEmpty(vecchia_password)){
            String nouva_password = campo_nuova_password.getText().toString();
            if(!TextUtils.isEmpty(nouva_password)){
                String ripeti_nouva_password = campo_ripeti_password.getText().toString();
                if(!TextUtils.isEmpty(ripeti_nouva_password)){
                    if(ripeti_nouva_password.equals(nouva_password)){
                        Controller.modifica_password(vecchia_password,nouva_password,this);
                    }else
                        Toast.makeText(this, "Le password non coincidono",
                                Toast.LENGTH_SHORT).show();
                }
            }
        }



    }

    public void Invia_richiesta_cancellazione_is_clicked(View view) {
        Controller.richiesta_cancellazione(this);
    }
}
