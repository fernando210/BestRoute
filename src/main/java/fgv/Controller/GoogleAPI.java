package fgv.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fgv.Model.MRota;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Fernando on 17/01/2017.
 */

public class GoogleAPI implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static String DIRECTION_API = "AIzaSyDODtIkOBbLPKCZ7kEHc9Dapv2430dXeNI";
    GoogleApiClient mGoogleApiClient;
    CPassageiro cp;
    public GoogleAPI(CPassageiro cpass){
        cp = cpass;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(cp, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(cp, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
//        Intent intent = new Intent("LOCATION_ACTION");
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("location", location);
//        intent.putExtras(bundle);
//        sendBroadcast(intent);location
    }
    private synchronized void createGoogleApi() {
        mGoogleApiClient = new GoogleApiClient.Builder(cp)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }
    public LatLng identificarLocalizacao(){

        try {
//            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();
//            // Getting the name of the best provider
//            String provider = lm.getBestProvider(criteria, true);
//            LatLng latLng = new LatLng(0,0);
//            if (ActivityCompat.checkSelfPermission(cp, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//            Location location = lm.getLastKnownLocation(provider);
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            latLng = new LatLng(latitude, longitude);
//
//            // Creating a LatLng object for the current location
//            return latLng;
            return null;
        }
        catch(Exception ex){
            return null;
        }

    }

//    public String getLatLongPorEndereco(String endereco){
//
//        StringWriter retornoTempo = new StringWriter();
//        try {
//            String stringUrl = "http://maps.google.com/maps/api/directions/json?";
//            stringUrl += "origin=" + latLong;
//            stringUrl += "&destination=" + destino;
//            stringUrl += "&key=" + DIRECTION_API;
//
//            URL url = new URL(stringUrl);
//            HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
//            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
//            {
//                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
//                String strLine = null;
//                while ((strLine = input.readLine()) != null)
//                {
//                    retornoTempo.append(strLine);
//                }
//                input.close();
//            }
//            String saidaJson = retornoTempo.toString();
//            JSONObject jsonObj = new JSONObject(saidaJson);
//            JSONArray ArrayRotas = jsonObj.getJSONArray("routes");
//            JSONObject route = ArrayRotas.getJSONObject(0);
//            JSONArray legs = route.getJSONArray("legs");
//            JSONObject leg = legs.getJSONObject(0);
//            JSONObject durationObject = leg.getJSONObject("duration");
//            String duracao = durationObject.getString(retornoSolicitado);
//            return duracao;
//        }
//        catch (Exception e) {
//            return "erro: " + e.getMessage();
//        }
//    }

    public String identificarTempoProximoDestino(String latLong, String destino, String retornoSolicitado){

        StringWriter retornoTempo = new StringWriter();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String stringUrl = "https://maps.google.com/maps/api/directions/json?";
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

//    public void drawPath(String result) {
//        if (line != null) {
//            myMap.clear();
//        }
//        myMap.addMarker(new MarkerOptions().position(endLatLng).icon(
//                BitmapDescriptorFactory.fromResource(R.drawable.redpin_marker)));
//        myMap.addMarker(new MarkerOptions().position(startLatLng).icon(
//                BitmapDescriptorFactory.fromResource(R.drawable.redpin_marker)));
//        try {
//            // Tranform the string into a json object
//            final JSONObject json = new JSONObject(result);
//            JSONArray routeArray = json.getJSONArray("routes");
//            JSONObject routes = routeArray.getJSONObject(0);
//            JSONObject overviewPolylines = routes
//                    .getJSONObject("overview_polyline");
//            String encodedString = overviewPolylines.getString("points");
//            ArrayList<LatLng> list = decodePoly(encodedString);
//
//            for (int z = 0; z < list.size() - 1; z++) {
//                LatLng src = list.get(z);
//                LatLng dest = list.get(z + 1);
//                line = myMap.addPolyline(new PolylineOptions()
//                        .add(new LatLng(src.latitude, src.longitude),
//                                new LatLng(dest.latitude, dest.longitude))
//                        .width(5).color(Color.BLUE).geodesic(true));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private ArrayList<LatLng> decodePoly(String encoded) {
//
//        ArrayList<LatLng> poly = new ArrayList<LatLng>();
//        int index = 0, len = encoded.length();
//        int lat = 0, lng = 0;
//
//        while (index < len) {
//            int b, shift = 0, result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lat += dlat;
//
//            shift = 0;
//            result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lng += dlng;
//
//            LatLng p = new LatLng((((double) lat / 1E5)),
//                    (((double) lng / 1E5)));
//            poly.add(p);
//        }
//
//        return poly;
//    }

    public String identificarDistanciaProximoDestino(String latLong, String destino){

        StringWriter retornoTempo = new StringWriter();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            String stringUrl = "https://maps.google.com/maps/api/directions/json?";
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
            JSONObject distanceObject = leg.getJSONObject("distance");
            String valor = distanceObject.getString("value");
            return valor.toString();
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
//                duracao = identificarTempoProximoDestino(rota.getPassageiros().get(i).getLogradouro(),
//                        rota.getDestino(),"value");
            }
            duracao = duracao.toLowerCase().replace("min","").replace("mins","").trim();
            rota.setTempoProxDest(rota.getPassageiros().get(i).getId(),Integer.valueOf(duracao));
            tempoTotal += Integer.valueOf(duracao);
        }
        rota.setTempoTotal(tempoTotal);
        return rota;
    }

    public void abrirRotaMaps(ArrayList<MRota> rota){



    }

}
