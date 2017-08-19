package fgv.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import fgv.DAO.CustomJsonObjectRequest;
import fgv.DAO.PassageiroDAO;

/**
 * Created by Fernando on 16/01/2017.
 */

public class MPassageiro {


    private int id;

    private String cpf;

    private String nome;

    private String telefone;

    private String logradouro;

    private String cidade;

    private String estado;

    private String bairro;

    private Double latitude;

    private Double longitude;

    private int idDestino;

    private String nomeResponsavel;

    private String telefoneResponsavel;

    private int ativo;

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public boolean inserirPassageiroVolley(RequestQueue rq, final Context contexto, Map<String,String> params, String url) {

        CustomJsonObjectRequest cjor = new CustomJsonObjectRequest(Request.Method.POST,
            url,
            params,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(contexto,"aee" + response.toString(),Toast.LENGTH_LONG);
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

    public ArrayList<MPassageiro> getAllPassageiros(RequestQueue rq, final Context contexto, Map<String,String> params, String url){
        CustomJsonObjectRequest cjor = new CustomJsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(contexto, response.toString(),Toast.LENGTH_LONG);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contexto, "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();

                    }

                });

        cjor.setTag("tagGetAllPassageiros");
        rq.add(cjor);
        return new ArrayList<MPassageiro>();

    }
    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }

    public boolean atualizarPassageiro(MPassageiro mp){
     //   setPassageiroDao();
        //return passageiroDao.atualizarPassageiro(mp);
        return false;
    }

    public void excluirPassageiro(){

    }

    public MPassageiro consultarPassageiro(String nome){
        //setPassageiroDao();
        return new MPassageiro();
        //return passageiroDao.consultarPassageiro(nome);
    }

}


