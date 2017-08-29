package fgv.Controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import fgv.DAO.PassageiroDAO;
import fgv.Model.MPassageiro;
import fgv.View.VAtualizarPassageiro;
import fgv.View.VPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CPassageiro {

    MPassageiro passageiro;
    GoogleAPI gApi;

    public CPassageiro(){
        passageiro = new MPassageiro();
        gApi = new GoogleAPI();
    }

    public boolean inserirPassageiroVolley(RequestQueue rq, Context contexto, MPassageiro p){

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("Ativo", "1");
        params.put("nome", p.getNome());
        params.put("cpf", p.getCpf());
        params.put("telefone", p.getTelefone());
        params.put("logradouro", p.getLogradouro());
        params.put("cidade", p.getCidade());
        params.put("estado", p.getEstado());
        params.put("bairro", p.getBairro());
        //params.put("idDestino", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("idMotorista", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("nomeResponsavel", p.getNomeResponsavel());
        params.put("telefoneResponsavel", p.getTelefoneResponsavel());

        params.put("latitude", String.valueOf(p.getLatitude()));
        params.put("longitude", String.valueOf(p.getLongitude()));

        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/InserirPassageiro";

        return passageiro.inserirPassageiroVolley(rq, contexto, params, url);
    }

    public void getAllPassageiros(RequestQueue rq, Context contexto, VPassageiro vp) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("motoristaId",1);

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/GetAllPassageiros?motoristaId=1";
        passageiro.getAllPassageiros(rq,contexto,vp,params,url);
    }

    public MPassageiro getPassageiro(ArrayList<MPassageiro> lst, String texto){
        for (int i = 0; i < lst.size();i++){
            if((lst.get(i).getNome() + " - " + lst.get(i).getCpf()).equals(texto)){
                return lst.get(i);
            }
        }
        return null;
    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        return new ArrayList<MPassageiro>();
    }
    public MPassageiro consultarPassageiro(String nome){
        return passageiro.consultarPassageiro(nome);
    }

    public void atualizarPassageiro(RequestQueue rq, Context contexto, MPassageiro p){

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("Id", String.valueOf(p.getId()));
        params.put("Ativo", String.valueOf(p.getAtivo()));
        params.put("nome", p.getNome());
        params.put("cpf", p.getCpf());
        params.put("telefone", p.getTelefone());
        params.put("logradouro", p.getLogradouro());
        params.put("cidade", p.getCidade());
        params.put("estado", p.getEstado());
        params.put("bairro", p.getBairro());
        //params.put("idDestino", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("idMotorista", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("nomeResponsavel", p.getNomeResponsavel());
        params.put("telefoneResponsavel", p.getTelefoneResponsavel());

        params.put("latitude", String.valueOf(p.getLatitude()));
        params.put("longitude", String.valueOf(p.getLongitude()));

        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/AtualizarPassageiro";
        passageiro.atualizarPassageiro(rq,contexto, params, url);
    }

    public void excluirPassageiro(){

    }

}
