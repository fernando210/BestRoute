package fgv.Model;

import android.content.Context;

import java.util.ArrayList;

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

    private PassageiroDAO passageiroDao;

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

    public PassageiroDAO getPassageiroDao() {
        return passageiroDao;
    }

    public void setPassageiroDao(PassageiroDAO passageiroDao) {
        this.passageiroDao = passageiroDao;
    }

    private void setPassageiroDao(){
        if(passageiroDao == null)
            passageiroDao = new PassageiroDAO();
    }

    public boolean inserirPassageiro(MPassageiro mp){
        setPassageiroDao();
        return passageiroDao.inserirPassageiro(mp);
    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }

    public boolean atualizarPassageiro(MPassageiro mp){
        setPassageiroDao();
        return passageiroDao.atualizarPassageiro(mp);
    }

    public void excluirPassageiro(){

    }

    public MPassageiro consultarPassageiro(String nome){
        setPassageiroDao();
        return passageiroDao.consultarPassageiro(nome);
    }

}


