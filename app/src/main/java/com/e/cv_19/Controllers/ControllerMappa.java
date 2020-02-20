package com.e.cv_19.Controllers;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.e.cv_19.Activities.Activity_mostra_struttura;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ControllerMappa {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference Strutture = database.collection("Strutture");
    private FragmentActivity Context;

    public ControllerMappa(FragmentActivity Activity) {
        Context = Activity;
    }

    public void Inserimento_marker_strutture(final GoogleMap mMap) {
        Strutture.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot Struttura : task.getResult()){
                            Map<String,Object> dati = Struttura.getData();
                            Double lat = (Double) dati.get("latitudine");
                            Double lng = (Double) dati.get("longitudine");
                            String nome = (String) dati.get("nome");
                            LatLng coordinate_posizione_struttura = new LatLng(lat.doubleValue(),lng.doubleValue());;
                            mMap.addMarker(new MarkerOptions().position(coordinate_posizione_struttura).title(nome)
                                    .snippet(Descrizione_struttura((String)dati.get("tipologia"),(String)dati.get("indirizzo"),(String)dati.get("citta"))))
                                    .setIcon(SetColore((String)dati.get("tipologia")));
                        }
                    }
                }
            });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final String nome = marker.getTitle();
                Strutture.whereEqualTo("nome",nome).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                            StrutturaSelezionata(document.getId());
                        }
                    }
                });
            }
        });
    }

    private void StrutturaSelezionata(String id_struttura){
        Intent mostra_struttura = new Intent(Context, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        Context.startActivity(mostra_struttura);

    }

    private BitmapDescriptor SetColore(String tipologia) {
        if(tipologia.equals("Ris")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }else if(tipologia.equals("Hot")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        }else{
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }

    }

    private String Descrizione_struttura(String tipologia, String indirizzo, String città) {
        return tipologia+","+indirizzo+","+città;
    }

}
