package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
public class Activity_risultati_ricerca extends AppCompatActivity {

    private ListView risultati_ricerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risultati_ricerca);

        risultati_ricerca = findViewById(R.id.Risultati_ricerca);
    }

    public void click_on_indietro(View view) {
    }


    public void click_on_menù(View view) {
    }
}
