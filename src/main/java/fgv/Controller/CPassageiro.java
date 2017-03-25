package fgv.Controller;

import java.util.ArrayList;
import fgv.DAO.PassageiroDAO;
import fgv.Model.MPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CPassageiro {

    MPassageiro passageiro = new MPassageiro();

    private void setMPassageiro(){
        if(passageiro == null)
            passageiro = new MPassageiro();
    }
    public boolean inserirPassageiro(MPassageiro p){
        return passageiro.inserirPassageiro(p);
    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }

    public MPassageiro consultarPassageiro(int id){
        setMPassageiro();
        return passageiro.consultarPassageiro(id);
    }

    public void alterarPassageiro(){

    }

    public void excluirPassageiro(){

    }

}
