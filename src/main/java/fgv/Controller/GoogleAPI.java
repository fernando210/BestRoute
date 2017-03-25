package fgv.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import fgv.Model.MRota;

/**
 * Created by Fernando on 17/01/2017.
 */

public class GoogleAPI extends Core {

    private final static String DIRECTION_API = "AIzaSyDODtIkOBbLPKCZ7kEHc9Dapv2430dXeNI";
    public LatLng identificarLocalizacao() {

        try {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // Getting the name of the best provider
            String provider = lm.getBestProvider(criteria, true);

            Location location = lm.getLastKnownLocation(provider);

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            return latLng;
        }
        catch(Exception ex){
            return null;
        }

    }

    public String identificarTempoProximoDestino(String latLong, String destino, String retornoSolicitado){

        StringWriter retornoTempo = new StringWriter();
        try {
            String stringUrl = "http://maps.google.com/maps/api/directions/json?";
            stringUrl += "origin=" + latLong;
            stringUrl += "&destination=" + destino;
            stringUrl += "&key=" + DIRECTION_API;

            URL url = new URL(stringUrl);
            HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null)
                {
                    retornoTempo.append(strLine);
                }
                input.close();
            }
            String saidaJson = retornoTempo.toString();
            JSONObject jsonObj = new JSONObject(saidaJson);
            JSONArray ArrayRotas = jsonObj.getJSONArray("routes");
            JSONObject route = ArrayRotas.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs");
            JSONObject leg = legs.getJSONObject(0);
            JSONObject durationObject = leg.getJSONObject("duration");
            String duracao = durationObject.getString(retornoSolicitado);
            return duracao;
        }
        catch (Exception e) {
            return "erro: " + e.getMessage();
        }
    }

    public MRota calcularTempoDeRota(MRota rota)throws Exception{

        String duracao = "";
        int tempoTotal = 0;

        for (int i = 0; i < rota.getPassageiros().size(); i++){
            if((i+1) < rota.getPassageiros().size()){
                duracao = identificarTempoProximoDestino(rota.getPassageiros().get(i).getLogradouro(),
                        rota.getPassageiros().get(i+1).getLogradouro(),"value");
            }
            else{
                duracao = identificarTempoProximoDestino(rota.getPassageiros().get(i).getLogradouro(),
                        rota.getDestino(),"value");
            }
            duracao = duracao.toLowerCase().replace("min","").replace("mins","").trim();
            rota.setTempoProxDest(rota.getPassageiros().get(i).getId(),Integer.valueOf(duracao));
            tempoTotal += Integer.valueOf(duracao);
        }
        rota.setTempoTotal(tempoTotal);
        return rota;
    }
}
