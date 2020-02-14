package com.e.cv_19.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Model.Recensione;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class RecensioniStrutturaAdapter extends FirestoreRecyclerAdapter<Recensione, RecensioniStrutturaAdapter.NoteHolder>{

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private  OnItemClickListner listner;

    public RecensioniStrutturaAdapter(@NonNull FirestoreRecyclerOptions<Recensione> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final NoteHolder noteHolder, int i, @NonNull Recensione recensioni) {
        final DocumentReference dati_Autore = database.collection("Utenti").document(recensioni.getIdAutore());

        dati_Autore.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        noteHolder.Nickname.setText(document.getString("nickname"));
                    }

                }
            }
        });
        noteHolder.Testo.setText(recensioni.getTesto());
        noteHolder.Valutazione.setText(String.valueOf(recensioni.getVoto()));
    }




    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.riga_lista_recensioni_struttura,
                parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView Nickname;
        TextView Testo;
        TextView Valutazione;


        public NoteHolder(View itemView) {
            super(itemView);
            Nickname = itemView.findViewById(R.id.textViewNickname);
            Testo=itemView.findViewById(R.id.textViewTestoRecensione);
            Valutazione=itemView.findViewById(R.id.textViewValutazione);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listner!=null){
                        listner.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListner{
        void onItemClick(DocumentSnapshot docSnapshot,int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){

        this.listner = listner;
    }
}
