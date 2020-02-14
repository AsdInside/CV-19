package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_menu extends AppCompatActivity {

    private FirebaseAuth data_info = FirebaseAuth.getInstance();
    private FirebaseUser current_user = data_info.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void click_on_logout(View view) {
        Intent login =new Intent(this,Activity_login.class);
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(login);
    }

    public void click_on_ricerca(View view) {
        Intent intent_ricerca = new Intent(this,Activity_ricerca.class);
        startActivity(intent_ricerca);
    }

    public void click_on_recensioni(View view) {
        Intent intent_recensioni = new Intent(this,Activity_visualizza_recensioni.class);
        startActivity(intent_recensioni);
    }

    public void click_on_impostazioni(View view) {
        Intent intent_impostazioni = new Intent(this,Activity_modifica_impostazioni.class);
        startActivity(intent_impostazioni);
    }

    public void click_on_indietro(View view) {
        finish();
    }
}
