package fgv.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fgv.DAO.CustomJsonObjectRequest;
import fgv.DAO.GsonRequest;
import fgv.View.VAtualizarPassageiro;
import fgv.View.VPassageiro;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Fernando on 16/01/2017.
 */

public class MPassageiro{


    private int Id;

    private String Cpf;

    private String Nome;

    private String Telefone;

    private String Logradouro;

    private String Cidade;

    private String Estado;

    private String Bairro;

    private Double Latitude;

    private Double Longitude;

    private int IdDestino;

    private String NomeResponsavel;

    private String TelefoneResponsavel;

    private int Ativo;

    public int getAtivo() {
        return Ativo;
    }

    public void setAtivo(int ativo) {
        this.Ativo = ativo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        this.Cpf = cpf;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.Logradouro = logradouro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        this.Bairro = bairro;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        this.Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        this.Longitude = longitude;
    }

    public int getIdDestino() {
        return IdDestino;
    }

    public void setIdDestino(int idDestino) {
        this.IdDestino = idDestino;
    }

    public String getNomeResponsavel() {
        return NomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.NomeResponsavel = nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return TelefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.TelefoneResponsavel = telefoneResponsavel;
    }

    public boolean inserirPassageiroVolley(RequestQueue rq, final Context contexto, final Map<String,String> params, String url) {

        CustomJsonObjectRequest cjor = new CustomJsonObjectRequest(Request.Method.POST,
            url,
            params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(contexto,"Passageiro cadastrado com sucesso!",Toast.LENGTH_LONG).show();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(contexto, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();
                }

        });

        cjor.setTag("tagInserir");
    
        rq.add(cjor);
        return false;
    }

    public void atualizarPassageiro(RequestQueue rq, final Context contexto, final Map<String,String> params, String url){

        CustomJsonObjectRequest cjor = new CustomJsonObjectRequest(Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(contexto,"Passageiro atualizado com sucesso!" +
                                response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contexto, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();

                    }

                });

        cjor.setTag("tagInserir");

        rq.add(cjor);


//        GsonRequest<MPassageiro> gReq = new GsonRequest<MPassageiro>(url, MPassageiro.class, null,
//                new Response.Listener<MPassageiro>() {
//                    @Override
//                    public void onResponse(MPassageiro response) {
//                        Toast.makeText(contexto, "passageiro atualizado com sucesso", Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(contexto, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return params;
//            }
//        };
//        rq.add(gReq);
    }

    public void getAllPassageiros(RequestQueue rq, final Context contexto, final VPassageiro vp,
                                  Map<String,String> params, String url){

        Type type = new TypeToken<ArrayList<MPassageiro>>() {}.getType();
        GsonRequest<ArrayList<MPassageiro>> gReq = new GsonRequest<ArrayList<MPassageiro>>(url, type, null,
                new Response.Listener<ArrayList<MPassageiro>>() {
                    @Override
                    public void onResponse(ArrayList<MPassageiro> response) {
                        vp.lstPassageiros = response;
                        for (int i = 0; i < response.size();i++){
                            vp.passageirosAdapter.add(response.get(i).getNome() + " - " + response.get(i).getCpf());
                        }
                        vp.passageirosAdapter.notifyDataSetChanged();
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

    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();
        return passageiros;
    }

    public void excluirPassageiro(){

    }

    public MPassageiro consultarPassageiro(String nome){
        //setPassageiroDao();
        return new MPassageiro();
        //return passageiroDao.consultarPassageiro(nome);
    }

}


