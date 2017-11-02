package fgv.Controller;

import android.app.Activity;
import android.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fgv.Controller.AgPmx.Cromossomo;
import fgv.Controller.AgPmx.PMX;
import fgv.Model.MPassageiro;
import fgv.Model.MPassageiroDistancia;
import fgv.Model.MRota;

/**
 * Created by Fernando on 16/01/2017.
 */

public class CRota extends Activity {

    private ArrayList<Cromossomo> populacao;
    public ArrayList<MPassageiroDistancia> lstPassageirosDistancias;
    private RequestQueue rq;
    private int _SIZEPOPULACAO = 0;
    private Double _TAXAMUTACAO = 0.5;
    private int _QTDEXECUCOES = 30;

    public CRota(){
        populacao = new ArrayList<Cromossomo>();
    }

    public MRota consultarRota(){
        MRota rota = new MRota();

        return rota;
    }

    public void calcularMelhorRota(ArrayList<MPassageiro> lstPassageiros, ArrayList<Cromossomo> populacaoOld){

        ArrayList<Cromossomo> populacao = populacaoOld;

        for (int i = 0; i < _QTDEXECUCOES; i++){
            populacao = executaAg(lstPassageiros, populacao);
        }
        String passageirosIds = "";
        for(int j = 0; j < populacao.size(); j++){
            passageirosIds = String.valueOf(populacao.get(j).getCromossomoId()) + ";";
        }
    }


    private ArrayList<Cromossomo> executaAg(ArrayList<MPassageiro> lstPassageiros, ArrayList<Cromossomo> populacaoOld){
        ArrayList<Cromossomo> popAux = new ArrayList<Cromossomo>();
        ArrayList<Cromossomo> novaPopulacao = new ArrayList<Cromossomo>();

        try {
            _SIZEPOPULACAO = lstPassageiros.size();
            //carrega as distancias entre passageiros
            getPassageirosDistancias(lstPassageiros);

            //prende a execucao ate preencher a lista de distancias
            while (lstPassageirosDistancias.size() == 0)
                Thread.sleep(300);

            //----------Executa o AG para calcular a melhor rota----------------

            //primeiro passo: criacao da populacao e calculo de fitness individual
            //**criar random para gerar pop aleatoriamente
            for (int i = 0; i < lstPassageiros.size(); i++){
                populacao.add(new Cromossomo(lstPassageiros.get(i).getId()));

                if(i > 0){
                    //calcula fitness local
                    populacao.get(i).setFitness_PerGen(calcularFitness(lstPassageiros.get(i - 1).getId(), lstPassageiros.get(i).getId()));
                }
            }

            //calcula fitness percent
            calcularFitnessPercent();

            //calcula range da roleta
            calcularRangeRoleta();

            for(int i = 0; i < (_SIZEPOPULACAO/2); i++)
            {
                //Selecionar os pais para cruzamento
                Cromossomo pai = Roleta(populacaoOld);
                Cromossomo mae = Roleta(populacaoOld);

                //Realizar o Cruzamento
                Cromossomo[] filhos = new PMX(pai, mae);

                //Aplicar a mutação
                Cromossomo filhoA = Mutacao(filhos[0]);
                Cromossomo filhoB = Mutacao(filhos[1]);

                popAux.add(filhoA);
                popAux.add(filhoB);

            }

            //Apagar os velhos membros
            //Inserir novos Membros

            for(int i = 0; i < _SIZEPOPULACAO; i++)
            {
                novaPopulacao.set(i, popAux.get(i));
            }
            popAux = null;

            //Re-Avaliar a pop
            atualizarValores(novaPopulacao);

            return novaPopulacao;

        }
        catch(Exception e){

        }
    }

    private void getPassageirosDistancias(ArrayList<MPassageiro> lstPassageiros){
        MRota rotaModel = new MRota();
        rq = Volley.newRequestQueue(getBaseContext());

        Map<String, String> params;
        params = new HashMap<String,String>();

        for (int i = 0; i < lstPassageiros.size();i++){
            params.put("id", String.valueOf(lstPassageiros.get(i).getId()));
        }

        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/GetPassageirosDistancias";
        rotaModel.getPassageiroDistancia(rq, getBaseContext(), this, params, url);
    }

    public double calcularFitness(int passageiroInicial, int passageiroFinal){

        for (int j = 0; j < lstPassageirosDistancias.size(); j++){
            if(lstPassageirosDistancias.get(j).getIdPassageiroInicio() == passageiroInicial &&
                    lstPassageirosDistancias.get(j).getIdPassageiroFim() == passageiroFinal)
                return lstPassageirosDistancias.get(j).getDistancia();
        }
        return -1;
    }

    public void calcularFitnessPercent()
    {
        double somatoriaFitness = 0;
        //Somatoria de todos os valores de aptidao
        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            somatoriaFitness += populacao.get(i).getFitness();
        }

        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            populacao.get(i).setFitnessPercent((populacao.get(i).getFitness() * 100) / somatoriaFitness);
        }
    }

    public void calcularRangeRoleta()
    {
        //Primeiramente deve-se ordenar a populacao em ordem crescente
        //Chamar o metodo para ordenar a populacao
        ordenarPopulacao();
        double somatoria = 0;

        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            if(i == 0)
            {
                somatoria += populacao.get(i).getFitnessPercent();
                populacao.get(i).setRangeRoleta(0, somatoria);
            }
            else if(i == (_SIZEPOPULACAO - 1))
            {
                populacao.get(i).setRangeRoleta(somatoria, 100f);
            }
            else
            {
                populacao.get(i).setRangeRoleta(somatoria, somatoria + populacao.get(i).getFitnessPercent());
                somatoria += populacao.get(i).getFitnessPercent();
            }
        }
    }

    public void ordenarPopulacao(){
        Cromossomo aux = new Cromossomo(_SIZEPOPULACAO + 1,999);

        for (int i = 0; i < _SIZEPOPULACAO; i++)
        {
            for (int j = 0; j < _SIZEPOPULACAO; j++)
            {
                if (populacao.get(i).getFitnessPercent() < populacao.get(j).getFitnessPercent())
                {
                    aux = populacao.get(i);
                    populacao.set(i,populacao.get(j));
                    populacao.set(j,aux);
                }
            }
        }

    }

    public Cromossomo Roleta(ArrayList<Cromossomo> pop)
    {
        Random r = new Random();
        //esse random serve pra refinar o algoritmo, ou seja,
        //verificar valores entre 0,5 e 1; 0 e 1;etc...
        double numSorteado = (r.nextDouble() * 100);

        for(int i = 0; i < pop.size(); i++){
            if(numSorteado >= pop.get(i).getRangeRoleta()[0] && numSorteado <= pop.get(i).getRangeRoleta()[1])
            {
                return pop.get(i);
            }
        }
        //Nunca vai acontecer
        return null;
    }

    public Cromossomo Mutacao(Cromossomo ind)
    {
        //Verificar como sera a mutacao para esse caso
        return new Cromossomo(0,0);
    }

    public void atualizarValores(ArrayList<Cromossomo> pop)
    {
        for (int i = 0; i < pop.size(); i++){
            //CalcularFitness
            calcularFitness(pop.get(i).getCromossomoId(),pop.get(i + 1).getCromossomoId());

            //CalcularFitnessPercent;
            calcularFitnessPercent();

            //CalcularRangeRoleta
            calcularRangeRoleta();
        }

    }

    public void registrarMelhoresRotas(){

    }

    public void verificarMotoristaChegando(){

    }

    public void verificarTempoTrajeto(){

    }

    public void calcularTempoDeRota(){

    }

    public void executarRotaSelecionada(){

    }

    public void buscarPassageiro(){

    }

    public void confirmarEntregaDestino(){

    }

    public void confirmarBusca(){

    }

    public void confirmarEntregaCasa(){

    }

}
