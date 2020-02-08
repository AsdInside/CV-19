package com.e.cv_19.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.StruttureAdapter;
import com.e.cv_19.Model.Strutture;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Main_Activity extends AppCompatActivity {

    private TextView campo_ricerca;
    private RecyclerView lista_strutture;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Strutture");
    private StruttureAdapter adapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_strutture = findViewById(R.id.Strutture);

        setUpRecyclerView();
        adapter.setOnItemClickListner(new StruttureAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(DocumentSnapshot docSnapshot, int position) {
                Strutture s=docSnapshot.toObject(Strutture.class);
                String id=docSnapshot.getId();
                gotoPage(s,id);
            }
        });

    }
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){

        super.onStop();
        adapter.stopListening();
    }

    private void gotoPage(Strutture s, String id) {
    //TODO metodo da implementare
    }

    //Recupero dati dal db


    public void Ricerca(View view) {
    }

    public void click_on_map(View view) {
    }

    public void click_on_località_turistiche(View view) {
    }



    public void click_on_hotel(View view) {
    }

    public void click_on_ristoranti(View view) {
    }
    private void setUpRecyclerView() {
        Query ordinamento = notebookRef.orderBy("valutazione",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(ordinamento,Strutture.class).build();
        adapter = new StruttureAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.Strutture);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void click_on_menù(View view) {
    }
}
