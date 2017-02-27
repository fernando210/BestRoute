package fgv.Controller;

import java.util.ArrayList;

import fgv.DAO.PassageiroDAO;
import fgv.Model.MPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CPassageiro {

    MPassageiro passageiro;

    private void setMPassageiro(){
        if(passageiro == null)
            passageiro = new MPassageiro();
    }
    public void inserirPassageiro(){

    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }

    public ArrayList<MPassageiro> consultarPassageiro(){
        setMPassageiro();
        return passageiro.consultarPassageiro();
    }

    public void alterarPassageiro(){

    }

    public void excluirPassageiro(){

    }

}
