package fgv.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import fgv.Controller.CRota;

/**
 * Created by Fernando on 16/01/2017.
 */

public class MRota {

    private int cdRota;
    private int destino;
    private ArrayList<MPassageiro> passageiros;
    private Map<Integer,Integer > tempoProxDest = new HashMap<Integer, Integer>();
    private int tempoTotal;
    private double fitness;
    private double fitnessPercent;
    private double[] faixaRoleta = { 0, 0 };            // Piece of the pizza which it can be selected //

    public void setRangeRoleta(double inicio, double fim)
    {
        faixaRoleta[0] = inicio;
        faixaRoleta[1] = fim;
    }

    public double[] getRangeRoleta()
    {
        return this.faixaRoleta;
    }

    public double getFitnessRota(){
        return fitness;
    }

    public void setFitnessRota(double fitness){
        this.fitness = fitness;
    }

    public double getFitnessPercent(){
        return fitnessPercent;
    }

    public void setFitnessPercent(double fitnessPercent){
        this.fitnessPercent = fitnessPercent;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public void setTempoProxDest(int cdPassageiro, int duracao){
        this.tempoProxDest.put(cdPassageiro,duracao);
    }
    public int getTempoProxDest(int cdPassageiro){
        return this.tempoProxDest.get(cdPassageiro);
    }

    public int getCdRota() {
        return cdRota;
    }

    public void setCdRota(int cdRota) {
        this.cdRota = cdRota;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public ArrayList<MPassageiro> getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(ArrayList<MPassageiro> passageiros) {
        this.passageiros = passageiros;
    }

    public MRota consultarRota(){
        MRota rota = new MRota();

        return rota;
    }

    public void getPassageiroDistancia(RequestQueue rq, final Context contexto, final CRota cRota,
                                       final Map<String,String> params, String url, final CRota cr, final ProgressDialog mProgressDialog){
        Type type = new TypeToken<ArrayList<MPassageiroDistancia>>() {}.getType();

        GsonRequest<ArrayList<MPassageiroDistancia>> gReq = new GsonRequest<ArrayList<MPassageiroDistancia>>(url, type, null,
                new Response.Listener<ArrayList<MPassageiroDistancia>>() {
                    @Override
                    public void onResponse(ArrayList<MPassageiroDistancia> response) {
                        cRota.lstPassageirosDistancias = response;
                        cRota.calcularMelhorRota(cr, mProgressDialog);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contexto, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        rq.add(gReq);
    }

    public void calcularMelhorRota(){

    }

    public void registrarMelhoresRotas(){

    }

    public void verificarMotoristaChegando(){

    }

    public void verificarTempoTrajeto(){

    }


}
