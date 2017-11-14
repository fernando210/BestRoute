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

    private ArrayList<Cromossomo> rota;
    private ArrayList<MRota> populacao;
    public ArrayList<MPassageiroDistancia> lstPassageirosDistancias;
    private RequestQueue rq;
    private int _SIZEPOPULACAO = 0;
    private Double _TAXAMUTACAO = 0.5;
    private int _QTDEXECUCOES = 30;

    public CRota(){
        populacao = new ArrayList<MRota>();
    }

    public MRota consultarRota(){
        MRota rota = new MRota();

        return rota;
    }

    public void calcularMelhorRota(ArrayList<MPassageiro> lstPassageiros, ArrayList<MRota> populacaoOld){

        ArrayList<MRota> populacao = populacaoOld;

        for (int i = 0; i < _QTDEXECUCOES; i++){
            populacao = executaAg(lstPassageiros, populacao);
        }
        String passageirosIds = "";
        for(int j = 0; j < populacao.size(); j++){
            passageirosIds = String.valueOf(populacao.get(j).getPassageiros().get(j).getId()) + ";";
        }
    }

    private ArrayList<MRota> executaAg(ArrayList<MPassageiro> lstPassageiros, ArrayList<MRota> populacaoOld){
        ArrayList<MRota> popAux = new ArrayList<MRota>();
        ArrayList<MRota> novaPopulacao = new ArrayList<MRota>();
        String passInseridos = "";
        MRota newRota = new MRota();
        ArrayList<MPassageiro> passageirosNewRota = new ArrayList<MPassageiro>();
        int randPassageiro = 0;
        int indexPass = 0;

        try {
            _SIZEPOPULACAO = 20;
            //carrega as distancias entre passageiros
            getPassageirosDistancias(lstPassageiros);

            //prende a execucao ate preencher a lista de distancias
            while (lstPassageirosDistancias.size() == 0)
                Thread.sleep(300);

            //----------Executa o AG para calcular a melhor rota----------------

            //primeiro passo: criacao da populacao e calculo de fitness individual
            //CRIAÇÃO ALEATÓRIA DE POPULAÇÃO INICIAL

            while(populacao.size() < _SIZEPOPULACAO){

                while (newRota.getPassageiros().size() < lstPassageiros.size()){
                    //TODO: Verificar pra nao gerar indexes repetidos
                    randPassageiro = (int)(Math.random() * (lstPassageiros.size()));
                    passageirosNewRota.add(lstPassageiros.get(randPassageiro));

                    //calcula fitness local do passageiro anterior, pois o fitness eh a distancia ate o proximo ponto
                    if(passageirosNewRota.size() == lstPassageiros.size()){
                        indexPass = passageirosNewRota.size()-1;
                        passageirosNewRota.get(indexPass).setFitness(
                                calcularFitness(passageirosNewRota.get(indexPass).getId(),passageirosNewRota.get(0).getId())
                        );
                    }
                    else if(passageirosNewRota.size() > 1){
                        indexPass = passageirosNewRota.size()-2;
                        passageirosNewRota.get(indexPass).setFitness(
                                calcularFitness(passageirosNewRota.get(indexPass).getId(),
                                        passageirosNewRota.get(passageirosNewRota.size()-1).getId())
                        );
                    }
                }
                newRota.setPassageiros(passageirosNewRota);
                newRota.setFitnessRota(calcularFitnessRota(newRota));
                newRota = new MRota();
            }

            //calcula fitness percent
            calcularFitnessPercent(populacao);

            //calcula range da roleta
            calcularRangeRoleta(populacao);

            for(int i = 0; i < (_SIZEPOPULACAO/2); i++)
            {
                //Selecionar os pais para cruzamento
                MRota paiAux = Roleta(populacaoOld);
                MRota maeAux = Roleta(populacaoOld);

                //1 - criar array de int, com os ids dos passageiros
                int [] pai = new int [paiAux.getPassageiros().size()] ;
                int [] mae = new int [maeAux.getPassageiros().size()];
                int count;

                for (count = 0; count < paiAux.getPassageiros().size();count++){
                    pai[count] = paiAux.getPassageiros().get(count).getId();
                }

                for (count = 0; count < maeAux.getPassageiros().size();count++){
                    mae[count] = maeAux.getPassageiros().get(count).getId();
                }

                //2 -Realizar o Crossover
                PMX filhos = new PMX(pai, mae);

                //3 - Converter o resultado para uma rota

                //pegar arrays de int criado pelo pmx
                pai = filhos.get_offspring1();
                mae = filhos.get_offspring2();

                MPassageiro mp = new MPassageiro();
                ArrayList<MPassageiro> lstPassageirosPai = new ArrayList<MPassageiro>();
                ArrayList<MPassageiro> lstPassageirosMae = new ArrayList<MPassageiro>();
                //Pai e mae tem o mesmo length, entao pode ser o length de qualquer um
                for (count = 0; count < pai.length; count ++){
                    lstPassageirosPai.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, pai[count])));

                    lstPassageirosMae.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, mae[count])));
                }

                paiAux.setPassageiros(lstPassageirosPai);
                maeAux.setPassageiros(lstPassageirosMae);

                //Aplicar a mutação
                MRota filhoA = Mutacao(paiAux);
                MRota filhoB = Mutacao(maeAux);

                //calcular fitness individuais
                calcularFitnessLocais(filhoA.getPassageiros());
                calcularFitnessLocais(filhoB.getPassageiros());

                //calcular fitness das rotas
                calcularFitnessRota(filhoA);
                calcularFitnessRota(filhoB);

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

            //Re-Avaliar a populacao
            atualizarValores(novaPopulacao);

            return novaPopulacao;

        }
        catch(Exception e){
            return null;
        }
    }

    public double calcularFitness(int passageiroInicial, int passageiroFinal){

        for (int j = 0; j < lstPassageirosDistancias.size(); j++){
            if(lstPassageirosDistancias.get(j).getIdPassageiroInicio() == passageiroInicial &&
                    lstPassageirosDistancias.get(j).getIdPassageiroFim() == passageiroFinal)
                return lstPassageirosDistancias.get(j).getDistancia();
        }
        return -1;
    }

    public void calcularFitnessLocais(ArrayList<MPassageiro> passageiros){
        for (int i = 0; i < passageiros.size(); i++){
            if(i == passageiros.size()){
                passageiros.get(i).setFitness(
                        calcularFitness(passageiros.get(i).getId(),passageiros.get(0).getId())
                );
            }
            else if(passageiros.size() > 1){
                passageiros.get(i).setFitness(
                        calcularFitness(passageiros.get(i).getId(),
                                passageiros.get(passageiros.size()-1).getId())
                );
            }
        }
    }

    public double calcularFitnessRota(MRota rota){

        double somatoriaTotal = 0;
        for (int i = 0; i < rota.getPassageiros().size(); i++){
            somatoriaTotal += rota.getPassageiros().get(i).getFitness();
        }
        return somatoriaTotal / rota.getPassageiros().size();
    }

    public void calcularFitnessPercent(ArrayList<MRota> pop)
    {
        double somatoriaFitness = 0;
        //Somatoria de todos os valores de aptidao
        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            somatoriaFitness += pop.get(i).getFitnessRota();
        }

        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            pop.get(i).setFitnessPercent((pop.get(i).getFitnessRota() * 100) / somatoriaFitness);
        }
    }

    public void calcularRangeRoleta(ArrayList<MRota> pop)
    {
        //Primeiramente deve-se ordenar a populacao em ordem crescente
        //Chamar o metodo para ordenar a populacao
        ordenarPopulacao(pop);
        double somatoria = 0;

        for(int i = 0; i < _SIZEPOPULACAO; i++)
        {
            if(i == 0)
            {
                somatoria += pop.get(i).getFitnessPercent();
                pop.get(i).setRangeRoleta(0, somatoria);
            }
            else if(i == (_SIZEPOPULACAO - 1))
            {
                pop.get(i).setRangeRoleta(somatoria, 100f);
            }
            else
            {
                pop.get(i).setRangeRoleta(somatoria, somatoria + pop.get(i).getFitnessPercent());
                somatoria += pop.get(i).getFitnessPercent();
            }
        }
    }

    public void ordenarPopulacao(ArrayList<MRota> pop){
        MRota aux = new MRota();

        for (int i = 0; i < _SIZEPOPULACAO; i++)
        {
            for (int j = 0; j < _SIZEPOPULACAO; j++)
            {
                if (pop.get(i).getFitnessPercent() < pop.get(j).getFitnessPercent())
                {
                    aux = pop.get(i);
                    pop.set(i,pop.get(j));
                    pop.set(j,aux);
                }
            }
        }

    }

    public MRota Roleta(ArrayList<MRota> pop)
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

    public MRota Mutacao(MRota ind)
    {
        //TODO: Verificar como sera a mutacao para esse caso
        return ind;
    }

    private int getIndexPassageiroPorId(ArrayList<MPassageiro> passageiros, int id){
        for (int i = 0; i< passageiros.size(); i++){
            if(passageiros.get(i).getId() == id)
                return i;
        }
        return -1;
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

    public void atualizarValores(ArrayList<MRota> pop)
    {
        for (int i = 0; i < pop.size(); i++){
            //CalcularFitness
            calcularFitnessRota(pop.get(i));

            //CalcularFitnessPercent;
            calcularFitnessPercent(pop);

            //CalcularRangeRoleta
            calcularRangeRoleta(pop);
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
