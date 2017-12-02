package fgv.Controller;

import java.util.Date;

import fgv.Model.MPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CCalendario {

    private int cdPassageiro;
    private Date data;

    public int getCdPassageiro() {
        return cdPassageiro;
    }

    public void setCdPassageiro(int cdPassageiro) {
        this.cdPassageiro = cdPassageiro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean verificarComparecimento(MPassageiro passageiro){
        return false;
    }

    public void marcarData(){

    }

    public void desmarcarData(){

    }

}
