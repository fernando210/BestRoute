package fgv.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

import fgv.Model.MPassageiro;
import fgv.Model.MRota;

public class CMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MRota rota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent iMapa = getIntent();
        Bundle extras = iMapa.getExtras();
        if(extras != null) {
            rota =  (new Gson()).fromJson(extras.get("rota").toString(),MRota.class);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        desenharRota(rota);

//        mMap = googleMap;
//
//        // Add a marker in Fatec, Sao Paulo, and move the camera.
//        LatLng fatec = new LatLng(-23.609182, -46.607663);
//        mMap.addMarker(new MarkerOptions().position(fatec).title("Fatec Ipiranga"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(fatec));
    }

    private void desenharRota(MRota rota){

        LatLng marcador;
        for (int i = 0; i < rota.getPassageiros().size(); i++){
            marcador = new LatLng(rota.getPassageiros().get(i).getLatitude(),
                    rota.getPassageiros().get(i).getLongitude());

            mMap.addMarker(new MarkerOptions().position(marcador).title(rota.getPassageiros().get(i).getNome()));
        }
    }
}