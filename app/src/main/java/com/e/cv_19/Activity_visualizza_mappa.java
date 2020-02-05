package com.e.cv_19;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_visualizza_mappa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager gestore_posizione_utente;
    private LocationListener aggiornamento_posizione_utente;


    //Verifica che l'utente accetti la richiesta da parte del dispositivo di conoscere la sua posizione attuale
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gestore_posizione_utente.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, aggiornamento_posizione_utente);
            }

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

    //TODO: eliminare solo il vecchio marker che segna la posizione dell'utente dalla mappa e acquisire le coordinate delle strutture
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gestore_posizione_utente = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        aggiornamento_posizione_utente = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng posizione_attuale_utente = new LatLng(location.getLatitude(),location.getLongitude());

                mMap.addMarker(new MarkerOptions().position(posizione_attuale_utente).title("Tu"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posizione_attuale_utente));

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
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }else{
            gestore_posizione_utente.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, aggiornamento_posizione_utente);

            Location ultima_posizione_utente = gestore_posizione_utente.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng coordinate_ultima_posizione = new LatLng(ultima_posizione_utente.getLatitude(),ultima_posizione_utente.getLongitude());

            mMap.addMarker(new MarkerOptions().position(coordinate_ultima_posizione).title("Tu"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate_ultima_posizione));
        }


    }
}
