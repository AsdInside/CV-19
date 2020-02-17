package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.Controllers.ControllerMain;
import com.e.cv_19.R;


public class Activity_menu extends AppCompatActivity {

    private ControllerMain Controller = new ControllerMain();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void click_on_logout(View view) {
        Controller.effettua_logout(this);
    }

    public void click_on_ricerca(View view) {
        Controller.ricerca_avanzata(this);
    }

    public void click_on_recensioni(View view) {
        Controller.mostra_recensioni_personali(this);
    }

    public void click_on_impostazioni(View view) {
        Controller.mostra_impostazioni(this);
    }

    public void click_on_indietro(View view) {
        finish();
    }
}
