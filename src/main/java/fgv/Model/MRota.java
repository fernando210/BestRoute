package fgv.Model;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fernando on 16/01/2017.
 */

public class MRota {

    private int cdRota;
    private String destino;
    private ArrayList<MPassageiro> passageiros;
    private Map<Integer,Integer > tempoProxDest = new HashMap<Integer, Integer>();
    private int tempoTotal;

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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
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

    public void calcularMelhorRota(){

    }

    public void registrarMelhoresRotas(){

    }

    public void verificarMotoristaChegando(){

    }

    public void verificarTempoTrajeto(){

    }


}
