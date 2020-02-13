package com.e.cv_19.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Adapter.RecensioniStrutturaAdapter;
import com.e.cv_19.Model.Recensioni;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Activity_visualizza_recensioni_struttura extends AppCompatActivity {

    private Spinner filtro_voto;
    private EditText campo_ricerca;
    private RecyclerView lista_recensioni;
    private RecensioniStrutturaAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id_struttura;
    private CollectionReference notebookRef = db.collection("Recensione");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensioni_struttura);

        filtro_voto = findViewById(R.id.spinnerVoto);
        ArrayAdapter<CharSequence> adapter_recensioni = ArrayAdapter.createFromResource(this, R.array.Recensioni, android.R.layout.simple_spinner_dropdown_item);
        filtro_voto.setAdapter(adapter_recensioni);
        campo_ricerca = findViewById(R.id.campo_ricerca);
        lista_recensioni = findViewById(R.id.ListaRecensioni);
        Bundle dati_ricevuti = getIntent().getExtras();
        id_struttura = dati_ricevuti.getString("id");
        configurazione_lista_recensioni();

    }
    /*wP7YlJPgwPW0GiUEEptk*/
    private void configurazione_lista_recensioni() {
       notebookRef.orderBy("voto").whereEqualTo("struttura",id_struttura).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                   for (DocumentSnapshot document : task.getResult()){
                       Log.i("Recensioni", document.getString("testo") + " " + document.get("idAutore") + " "  + document.get("voto").toString()
                               + " " + document.getString("struttura")  );
                   }
               }else{
                   Log.d("Recensioni","nessuna recensione");
               }
           }
       });
        /*FirestoreRecyclerOptions<Recensioni> options = new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(recensioni,Recensioni.class).build();
        adapter = new RecensioniStrutturaAdapter(options);


        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);*/
    }

    public void click_on_ristoranti(View view) {
        Intent Ricerca = new Intent(this,Activity_risultati_ricerca.class);
        Ricerca.putExtra("Tipo Struttura","ris");
        Ricerca.putExtra("Tipo ricerca","Category button");
        startActivity(Ricerca);
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

    public void click_on_filtra(View view) {
        String voto = filtro_voto.getSelectedItem().toString();
        if(voto.length() == 0){
            Toast.makeText(this, "Inserire il filtro dei voti", Toast.LENGTH_SHORT).show();
            return;
        }
        Query filtro = notebookRef.whereEqualTo("struttura",id_struttura).whereEqualTo("voto",voto)
                .orderBy("voto", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Recensioni> options = new FirestoreRecyclerOptions.Builder<Recensioni>().setQuery(filtro,Recensioni.class).build();
        adapter = new RecensioniStrutturaAdapter(options);


        lista_recensioni.setHasFixedSize(true);
        lista_recensioni.setLayoutManager(new LinearLayoutManager(this));
        lista_recensioni.setAdapter(adapter);
    }



    public void click_on_segnala(View view){
        //potrebbe essere necessario spostare questo metodo nell'adapter e cambiare il context del
        //layout riga recensioni struttura
    }

    public void click_on_menù(View view) {
        Intent intent_menù = new Intent(this,Activity_menu.class);
        startActivity(intent_menù);
    }
}
