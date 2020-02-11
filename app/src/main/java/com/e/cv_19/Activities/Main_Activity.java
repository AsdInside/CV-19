package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
                String id_struttura = docSnapshot.getId();
                gotoPage(id_struttura);
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

    private void gotoPage(String id_struttura) {
        Intent mostra_struttura = new Intent(this, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        startActivity(mostra_struttura);
    }




    public void Ricerca(View view) {
        if(!TextUtils.isEmpty(campo_ricerca.getText())){
            Intent Ricerca = new Intent(this, Activity_risultati_ricerca.class);
            Ricerca.putExtra("Nome Struttura", campo_ricerca.getText());
            Ricerca.putExtra("Tipo ricerca", "Per nome");
            startActivity(Ricerca);
        }else{
            Toast.makeText(this, "Inserire il nome di una struttura",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void click_on_map(View view) {
        Intent Mappa = new Intent(this,Activity_visualizza_mappa.class);
        startActivity(Mappa);
    }

    public void click_on_località_turistiche(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","tur");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }



    public void click_on_hotel(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","hot");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }

    public void click_on_ristoranti(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","ris");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
    }
    private void setUpRecyclerView() {
        Query ordinamento = notebookRef.orderBy("valutazione",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Strutture> options = new FirestoreRecyclerOptions.Builder<Strutture>().setQuery(ordinamento,Strutture.class).build();
        adapter = new StruttureAdapter(options);


        lista_strutture.setHasFixedSize(true);
        lista_strutture.setLayoutManager(new LinearLayoutManager(this));
        lista_strutture.setAdapter(adapter);
    }

    public void click_on_menù(View view) {
        Intent Intent_menù = new Intent(this,Activity_menu.class);
        startActivity(Intent_menù);
    }
}
