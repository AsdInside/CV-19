package com.e.cv_19.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cv_19.Model.Recensioni;
import com.e.cv_19.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class RecensioniStrutturaAdapter extends FirestoreRecyclerAdapter<Recensioni, RecensioniStrutturaAdapter.NoteHolder>{

    private  OnItemClickListner listner;

    public RecensioniStrutturaAdapter(@NonNull FirestoreRecyclerOptions<Recensioni> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteHolder noteHolder, int i, @NonNull Recensioni recensioni) {
        noteHolder.Nickname.setText(recensioni.getNickname());
        noteHolder.Testo.setText(recensioni.getTesto());
        noteHolder.Valutazione.setText(recensioni.getValutazione());
    }

    @NonNull
    @Override
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
