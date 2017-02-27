package fgv.Controller;

import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import fgv.Model.MRota;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CRelogio {

    MRota rota;

    public CRelogio(MRota rota){
        this.rota = rota;
    }

    public void calcularDistancia(){

    }

    public Map<LatLng, Integer> identificarLocalizacao(){
        Map<LatLng,Integer> retorno = new HashMap<LatLng,Integer>();
        GoogleAPI gApi = new GoogleAPI();
        LatLng latLng = gApi.identificarLocalizacao();
        gApi.identificarTempoProximoDestino(latLng.toString(),rota.getDestino(),"text");
        //solucoes para pegar index do passageiro: 1- contador que a cada mensagem exibida de passageiro pego incrementa 1
        //2- rodar o verificarLocalizacaoVeiculo() dentro de um for de passageiros.
        return retorno;
    }

    public void calcularTempo(){

    }

    public void enviarMensagemPassageiro(){

    }

    public void solicitarConfirmacaoChegada(){

    }

    public void solicitarConfirmacaoPassageiroPego(){

    }

    public Map<LatLng, Integer> verificarLocalizacaoVeiculo(){

        try {
            int c = 0;
            do {
                if(c == 20){
                    //TODO: Implementar para chamar mensagerias.
                    identificarLocalizacao();


                    c = 0;
                }

                Thread.sleep(1000);
                c++;
            }while (true);
        }
        catch (Exception ex){
            return null;
        }

    }

}
