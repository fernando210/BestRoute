package fgv.Model;

import android.content.Context;

import java.util.ArrayList;

import fgv.DAO.PassageiroDAO;

/**
 * Created by Fernando on 16/01/2017.
 */

public class MPassageiro {

    private int cdPassageiro;
    private String cpfPassageiro;
    private String nomePassageiro;
    private String telefonePassageiro;
    private String enderecoPassageiro;
    private int cdDestino;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private String emailResponsavel;

    private PassageiroDAO passageiroDao;

    public int getCdPassageiro() {
        return cdPassageiro;
    }

    public void setCdPassageiro(int cdPassageiro) {
        this.cdPassageiro = cdPassageiro;
    }

    public String getCpfPassageiro() {
        return cpfPassageiro;
    }

    public void setCpfPassageiro(String cpfPassageiro) {
        this.cpfPassageiro = cpfPassageiro;
    }

    public String getNomePassageiro() {
        return nomePassageiro;
    }

    public void setNomePassageiro(String nomePassageiro) {
        this.nomePassageiro = nomePassageiro;
    }

    public String getTelefonePassageiro() {
        return telefonePassageiro;
    }

    public void setTelefonePassageiro(String telefonePassageiro) {
        this.telefonePassageiro = telefonePassageiro;
    }

    public String getEnderecoPassageiro() {
        return enderecoPassageiro;
    }

    public void setEnderecoPassageiro(String enderecoPassageiro) {
        this.enderecoPassageiro = enderecoPassageiro;
    }

    public int getCdDestino() {
        return cdDestino;
    }

    public void setCdDestino(int cdDestino) {
        this.cdDestino = cdDestino;
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

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    private void setPassageiroDao(){
        
        Context c = null;
        if(passageiroDao == null)
            passageiroDao = new PassageiroDAO(c);
    }

    public void inserirPassageiro(){

    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }

    public void alterarPassageiro(){

    }

    public void excluirPassageiro(){

    }

    public ArrayList<MPassageiro> consultarPassageiro(){
        setPassageiroDao();
        return passageiroDao.consultarPassageiro();

    }


}


