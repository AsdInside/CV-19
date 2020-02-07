package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cv_19.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Main_Activity extends AppCompatActivity {

    private TextView campo_ricerca;
    private ListView lista_strutture;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Strutture");
    //private MainAdapter adapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_strutture = findViewById(R.id.Strutture);
    }

    //Recupero dati dal db


    public void Ricerca(View view) {
    }

    public void click_on_map(View view) {
    }

    public void click_on_località_turistiche(View view) {
    }

    public void click_on_menù_laterale(View view) {
    }

    public void click_on_hotel(View view) {
    }

    public void click_on_ristoranti(View view) {
    }
}
