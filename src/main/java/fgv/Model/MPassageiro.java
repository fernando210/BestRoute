package fgv.Model;

import android.content.Context;
import android.widget.Toast;

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

    @com.google.gson.annotations.SerializedName("id")
    private int id;
    @com.google.gson.annotations.SerializedName("cpf")
    private String cpf;
    @com.google.gson.annotations.SerializedName("nome")
    private String nome;
    @com.google.gson.annotations.SerializedName("telefone")
    private String telefone;
    @com.google.gson.annotations.SerializedName("logradouro")
    private String logradouro;
    @com.google.gson.annotations.SerializedName("cidade")
    private String cidade;
    @com.google.gson.annotations.SerializedName("estado")
    private String estado;
    @com.google.gson.annotations.SerializedName("bairro")
    private String bairro;
    @com.google.gson.annotations.SerializedName("latitude")
    private String latitude;
    @com.google.gson.annotations.SerializedName("longitude")
    private String longitude;
    @com.google.gson.annotations.SerializedName("idDestino")
    private int idDestino;
    @com.google.gson.annotations.SerializedName("nomeResponsavel")
    private String nomeResponsavel;
    @com.google.gson.annotations.SerializedName("telefoneResponsavel")
    private String telefoneResponsavel;
    @com.google.gson.annotations.SerializedName("ativo")
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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

    public boolean inserirPassageiroVolley(RequestQueue rq, final Context contexto, Map<String,String> params, String url){

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


    public boolean inserirPassageiro(MPassageiro mp){
        //setPassageiroDao();
        //return passageiroDao.inserirPassageiro(mp);
        return false;
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


