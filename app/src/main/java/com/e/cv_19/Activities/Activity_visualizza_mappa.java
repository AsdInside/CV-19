package com.e.cv_19.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.e.cv_19.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

public class Activity_visualizza_mappa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager gestore_posizione_utente;
    private LocationListener aggiornamento_posizione_utente;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference Strutture = database.collection("Strutture");


    //Verifica che l'utente accetti la richiesta da parte del dispositivo di conoscere la sua posizione attuale
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gestore_posizione_utente.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, aggiornamento_posizione_utente);
            }else{
                finish();
            }

        }else{
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_mappa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gestore_posizione_utente = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        aggiornamento_posizione_utente = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng posizione_attuale_utente = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(posizione_attuale_utente).title("Tu"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posizione_attuale_utente));
                Inserimento_marker_strutture();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Il dispositivo controlla di avere già ,o meno, i permessi per conoscere la posizione attuale dell'utente
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            gestore_posizione_utente.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, aggiornamento_posizione_utente);

            Location ultima_posizione_utente = gestore_posizione_utente.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng coordinate_ultima_posizione;
            if (ultima_posizione_utente != null) {
                coordinate_ultima_posizione = new LatLng(ultima_posizione_utente.getLatitude(),ultima_posizione_utente.getLongitude());
            }else{
                coordinate_ultima_posizione = new LatLng(40.838599,14.187656);
            }
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(coordinate_ultima_posizione).title("MSA"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate_ultima_posizione,10));
            Inserimento_marker_strutture();
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    final String nome = marker.getTitle();
                    Strutture.whereEqualTo("nome",nome).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                                gotoPage(document.getId());
                            }
                        }
                    });
                }
            });
        }


    }


    private void gotoPage(String id_struttura) {
        Intent mostra_struttura = new Intent(this, Activity_mostra_struttura.class);
        mostra_struttura.putExtra("id",id_struttura);
        finish();
        startActivity(mostra_struttura);
    }


    private void Inserimento_marker_strutture() {
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
