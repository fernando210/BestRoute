package fgv.Controller;

import android.app.Activity;
import android.content.Context;
import android.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private ArrayList<MRota> populacao;
    public ArrayList<MPassageiroDistancia> lstPassageirosDistancias = new ArrayList<MPassageiroDistancia>();
    private RequestQueue rq;
    private int _SIZEPOPULACAO = 0;
    private int _TAXAMUTACAO = 5;
    private int _QTDEXECUCOES = 30;
    private ArrayList<MPassageiro> lstPassageiros;
    private Context context;
    public CRota(){
        populacao = new ArrayList<MRota>();
    }

    public MRota consultarRota(){
        MRota rota = new MRota();

        return rota;
    }

    public void getDistancias(ArrayList<MPassageiro> lstPassageiros, Context context){
        this.lstPassageiros = lstPassageiros;
        this.context = context;
        //carrega as distancias entre passageiros
        if(lstPassageirosDistancias == null || lstPassageirosDistancias.size() == 0)
            getPassageirosDistancias(lstPassageiros, context);
    }

    public void calcularMelhorRota(){
        populacao = new ArrayList<MRota>();
        for (int i = 0; i < _QTDEXECUCOES; i++) {
            populacao = executaAg(lstPassageiros, populacao, context);
        }

//        String passageirosIds = "";
//        for(int j = 0; j < populacao.size(); j++){
//            passageirosIds = String.valueOf(populacao.get(j).getPassageiros().get(j).getId()) + ";";
//        }

    }

    private ArrayList<MRota> executaAg(ArrayList<MPassageiro> lstPassageiros, ArrayList<MRota> populacaoOld, Context context){
        ArrayList<MRota> popAux = new ArrayList<MRota>();
        ArrayList<MRota> novaPopulacao = new ArrayList<MRota>();
        String passInseridos = "";
        MRota newRota = new MRota();
        ArrayList<MPassageiro> passageirosNewRota = new ArrayList<MPassageiro>();
        int randPassageiro = 0;
        int indexPass = 0;
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        int contador = 0;

        try {
            _SIZEPOPULACAO = 15;

            //----------Executa o AG para calcular a melhor rota----------------

            //primeiro passo: criacao da populacao e calculo de fitness individual
            //CRIAÇÃO ALEATÓRIA DE POPULAÇÃO INICIAL

            if(populacaoOld == null || populacaoOld.size() == 0){

                populacao = new ArrayList<MRota>();
                while(populacao.size() < _SIZEPOPULACAO){
                    passageirosNewRota = new ArrayList<MPassageiro>();
                    numeros = new ArrayList<Integer>();
                    for (int i = 0; i < lstPassageiros.size(); i++) {
                        numeros.add(i);
                    }
                    contador = 0;

                    //Embaralhar os números:
                    Collections.shuffle(numeros);

                    newRota.setPassageiros(new ArrayList<MPassageiro>());
                    while (passageirosNewRota.size() < lstPassageiros.size()){
                        randPassageiro = numeros.get(contador);
                        passageirosNewRota.add(lstPassageiros.get(randPassageiro));

                        //calcula fitness local do passageiro anterior, pois o fitness eh a distancia ate o proximo ponto
                        if(passageirosNewRota.size() == lstPassageiros.size()){
                            indexPass = passageirosNewRota.size()-1;
                            //TODO: Testar quando for pro destino
                            passageirosNewRota.get(indexPass).setFitness(
                                    calcularFitness(passageirosNewRota.get(indexPass).getId(),2,true)
                            );
                        }
                        else if(passageirosNewRota.size() > 1){
                            indexPass = passageirosNewRota.size()-2;
                            passageirosNewRota.get(indexPass).setFitness(
                                    calcularFitness(passageirosNewRota.get(indexPass).getId(),
                                            passageirosNewRota.get(passageirosNewRota.size()-1).getId(), false)
                            );
                        }
                        contador++;
                    }
                    newRota.setPassageiros(passageirosNewRota);
                    newRota.setFitnessRota(calcularFitnessRota(newRota));
                    populacao.add(newRota);
                    newRota = new MRota();
                }

                //calcula fitness percent
                calcularFitnessPercent(populacao);
                //calcula range da roleta
                calcularRangeRoleta(populacao);

            }
            else{
                populacao = populacaoOld;
            }

            //TODO: O crossover eh so uma vez, e nao um for!!confirmar no curso
            for(int i = 0; i <= ((_SIZEPOPULACAO/2)); i++)
            {
                //Selecionar os pais para cruzamento
                //pq que as variavies de mrota tao vazias?
                MRota paiAux = Roleta(populacao);
                MRota maeAux = Roleta(populacao);
//                MRota paiAux = Roleta(populacaoOld);
//                MRota maeAux = Roleta(populacaoOld);

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

                for (count = 0; count < pai.length; count ++){
                    lstPassageirosPai.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, pai[count])));

                    lstPassageirosMae.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, mae[count])));
                }

                paiAux.setPassageiros(lstPassageirosPai);
                maeAux.setPassageiros(lstPassageirosMae);

                //Aplicar a mutação
                paiAux = ExchangeMutation(paiAux);
                maeAux = ExchangeMutation(maeAux);

                MRota filhoA = new MRota();
                filhoA.setPassageiros(paiAux.getPassageiros());

                MRota filhoB = new MRota();
                filhoB.setPassageiros(maeAux.getPassageiros());

//                //calcular fitness individuais
//                calcularFitnessLocais(filhoA.getPassageiros());
//                calcularFitnessLocais(filhoB.getPassageiros());
//
//                //calcular fitness das rotas
//                calcularFitnessRota(filhoA);
//                calcularFitnessRota(filhoB);

                popAux.add(filhoA);
                popAux.add(filhoB);
            }

            //Apagar os velhos membros
            //Inserir novos Membros

            for(int i = 0; i < _SIZEPOPULACAO; i++)
            {
                novaPopulacao.add(popAux.get(i));
            }
            popAux = null;

            //aplicar o 2opt
            for (int j = 0; j < novaPopulacao.size(); j++){
                novaPopulacao.get(j).setPassageiros(twoOpt(novaPopulacao.get(j).getPassageiros()));
            }

            //Re-Avaliar a populacao
            atualizarValores(novaPopulacao);

            return novaPopulacao;

        }
        catch(Exception e){
            return populacao;
        }
    }

    public double calcularFitness(int passageiroInicial, int passageiroFinal, boolean isDestino){

        if(!isDestino){
            for (int j = 0; j < lstPassageirosDistancias.size(); j++){
                if(lstPassageirosDistancias.get(j).getIdPassageiroInicio() == passageiroInicial &&
                        lstPassageirosDistancias.get(j).getIdPassageiroFim() == passageiroFinal)
                    return lstPassageirosDistancias.get(j).getDistancia();
            }
        }
        else {
            for (int j = 0; j < lstPassageirosDistancias.size(); j++){
                if(lstPassageirosDistancias.get(j).getIdPassageiroInicio() == passageiroInicial &&
                        lstPassageirosDistancias.get(j).getIdDestino() == passageiroFinal)
                    return lstPassageirosDistancias.get(j).getDistancia();
            }
        }
        return -1;
    }

    public void calcularFitnessLocais(ArrayList<MPassageiro> passageiros){
        for (int i = 0; i < passageiros.size(); i++){
            if(i == passageiros.size()){
                passageiros.get(i).setFitness(
                        calcularFitness(passageiros.get(i).getId(),2, true)
                );
            }
            else if(passageiros.size() > 1){
                passageiros.get(i).setFitness(
                        calcularFitness(passageiros.get(i).getId(),
                                passageiros.get(passageiros.size()-1).getId(),false)
                );
            }
        }
    }

    public double calcularFitnessRota(MRota rota){

        double somatoriaTotal = 0;
        for (int i = 0; i < rota.getPassageiros().size(); i++){
            somatoriaTotal += rota.getPassageiros().get(i).getFitness();
        }
        return Math.round(somatoriaTotal / rota.getPassageiros().size());
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
        double numSorteado = (numeroAleatorio(0,100));

        for(int i = 0; i < pop.size(); i++){
            if(numSorteado >= pop.get(i).getRangeRoleta()[0] && numSorteado <= pop.get(i).getRangeRoleta()[1])
            {
                return pop.get(i);
            }
        }
        //Nunca vai acontecer
        return null;
    }

    public MRota ExchangeMutation(MRota rota)
    {
        if(numeroAleatorio(0,100)  <= _TAXAMUTACAO){
            int primeiroRandom = numeroAleatorio(0,_SIZEPOPULACAO);
            int segundoRandom = numeroAleatorio(0,_SIZEPOPULACAO);

            MPassageiro mutacao1 = rota.getPassageiros().get(primeiroRandom);
            rota.getPassageiros().set(primeiroRandom, rota.getPassageiros().get(segundoRandom));
            rota.getPassageiros().set(segundoRandom, mutacao1);
        }
        return rota;
    }

    public int numeroAleatorio(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private ArrayList<MPassageiro> twoOpt(ArrayList<MPassageiro> passageiros){

        boolean modified = true;
        //se tiver menos de 4 passageiros nao precisa aplicar esse algoritmo
        if(passageiros.size() < 4)
            return passageiros;

        while (modified) {
            modified = false;

            for (int i = 0; i < passageiros.size(); i++) {
                for (int j = i+2; j+1 < passageiros.size(); j++) {

                    double d1 = calcularFitness(passageiros.get(i).getId(),passageiros.get(i+1).getId(), false) +
                            calcularFitness(passageiros.get(j).getId(), passageiros.get(j+1).getId(), false);

                    double d2 = calcularFitness(passageiros.get(i).getId(),passageiros.get(j).getId(), false) +
                            calcularFitness(passageiros.get(i+1).getId(), passageiros.get(j+1).getId(), false);

//                    double d1 = distanceTable.getDistanceBetween(tour.get(i), tour.get(i+1)) +
//                            distanceTable.getDistanceBetween(tour.get(j), tour.get(j+1));
//                    double d2 = distanceTable.getDistanceBetween(tour.get(i), tour.get(j)) +
//                            distanceTable.getDistanceBetween(tour.get(i+1), tour.get(j+1));


                    // Ajusta caso a distancia possa ser encurtada
                    if (d2 < d1) {
                        passageiros = trocarPosicoes(passageiros, i+1,j);
//                        tour.reverse(i+1, j);
                        modified = true;
                    }
                }
            }
        }
        return passageiros;
    }

    private ArrayList<MPassageiro> trocarPosicoes(ArrayList<MPassageiro> passageiros,
                                                  int primeiroIndex, int segundoIndice){

        MPassageiro passageiroAux = passageiros.get(primeiroIndex);
        passageiros.set(primeiroIndex, passageiros.get(segundoIndice));
        passageiros.set(segundoIndice, passageiroAux);
        return passageiros;
    }

    private int getIndexPassageiroPorId(ArrayList<MPassageiro> passageiros, int id){
        for (int i = 0; i< passageiros.size(); i++){
            if(passageiros.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    private void getPassageirosDistancias(ArrayList<MPassageiro> lstPassageiros, Context context){
        MRota rotaModel = new MRota();
        rq = Volley.newRequestQueue(context);

        Map<String, String> params;
        params = new HashMap<String,String>();

        for (int i = 0; i < lstPassageiros.size();i++){
            params.put("Id", String.valueOf(lstPassageiros.get(i).getId()));
        }
        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/GetPassageirosDistancias";
        rotaModel.getPassageiroDistancia(rq, context, this, params, url);
    }

    public void atualizarValores(ArrayList<MRota> pop)
    {
        for (int i = 0; i < pop.size(); i++){
            pop.get(i).setFitnessRota(calcularFitnessRota(pop.get(i)));
        }
        //CalcularFitnessPercent;
        calcularFitnessPercent(pop);

        //CalcularRangeRoleta
        calcularRangeRoleta(pop);

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
