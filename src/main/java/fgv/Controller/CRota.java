package fgv.Controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.MultiProcessor;
import com.google.gson.Gson;

import org.json.JSONException;
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
    private int _SIZEPOPULACAO;
    private int _TAXAMUTACAO;
    private int _QTDEXECUCOES;
    private Context context;
    CPassageiro cp = new CPassageiro();
    private ProgressDialog mprogressDialog;
    public Button btCalcularMelhorRota;
    MPassageiro passageiroFatec = new MPassageiro("Fatec Ipiranga", -23.609310, -46.607653);

    private EditText edQtdExecucao;
    private EditText edTaxaMutacao;

    public ArrayList<MPassageiro> lstPassageiros = new ArrayList<MPassageiro>();

    public CRota(){
        populacao = new ArrayList<MRota>();
    }

    @Override
    protected void onResume() {
        rq = Volley.newRequestQueue(getBaseContext());
        try {
            if(lstPassageiros.size() == 0)
                cp.getAllPassageiros(rq,getBaseContext(), null, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rota);

        edQtdExecucao = (EditText) findViewById(R.id.edQtdExecucao);
        edTaxaMutacao = (EditText) findViewById(R.id.edTaxaMutacao);

        btCalcularMelhorRota = (Button) findViewById(R.id.btCalcularMelhorRota);
        btCalcularMelhorRota.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                _QTDEXECUCOES = Integer.parseInt(edQtdExecucao.getText().toString());
                _TAXAMUTACAO = Integer.parseInt(edTaxaMutacao.getText().toString());
                CRota cr = new CRota();
                cr._QTDEXECUCOES = _QTDEXECUCOES;
                cr._TAXAMUTACAO = _TAXAMUTACAO;
                mprogressDialog = ProgressDialog.show(CRota.this, "Aguarde", "Calculando melhores rotas...");
                cr.getDistancias(lstPassageiros, getBaseContext(), CRota.this, mprogressDialog);
            }
        });

    }


    public void getDistancias(ArrayList<MPassageiro> lstPassageiros, Context context, CRota cr, ProgressDialog mProgressDialog){
        this.lstPassageiros = lstPassageiros;
        this.context = context;
        //carrega as distancias entre passageiros
        if(lstPassageirosDistancias == null || lstPassageirosDistancias.size() == 0)
            getPassageirosDistancias(lstPassageiros, context, cr, mProgressDialog);
    }

    public void calcularMelhorRota(Context ctx, ProgressDialog mProgressDialog){

        populacao = new ArrayList<MRota>();
        for (int i = 0; i < _QTDEXECUCOES; i++) {
            populacao = calcularMelhorRota(lstPassageiros, populacao, context);
        }

        try {
            //encerra progress dialog
            mProgressDialog.dismiss();
            Toast.makeText(ctx,"fitness da rota:" + populacao.get(0).getFitnessRota(),Toast.LENGTH_LONG).show();
            Intent iMapa = new Intent(ctx, CMapa.class);
            iMapa.putExtra("rota",
                    (new Gson()).toJson(populacao.get(0))
            );
            ctx.startActivity(iMapa);
        }
        catch (Exception ex){
            ex.getMessage();
        }

    }

    private ArrayList<MRota> calcularMelhorRota(ArrayList<MPassageiro> lstPassageiros, ArrayList<MRota> populacaoOld, Context context){
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
            _SIZEPOPULACAO = lstPassageiros.size();

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
                    //Adicionar destino
                    newRota.getPassageiros().add(passageiroFatec);
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

//            MRota paiOriginal;
//            MRota maeOriginal;
            for(int i = 0; i <= ((_SIZEPOPULACAO/2)); i++)
            {
                //Selecionar os pais para cruzamento
                MRota paiAux = roleta(populacao);
                MRota maeAux = roleta(populacao);

//                paiOriginal = paiAux;
//                maeOriginal = maeAux;
//                MRota paiAux = Roleta(populacaoOld);
//                MRota maeAux = Roleta(populacaoOld);

                //1 - criar array de int, com os ids dos passageiros
                int [] pai = new int [paiAux.getPassageiros().size() -1] ;
                int [] mae = new int [maeAux.getPassageiros().size() -1];
                int count;

                for (count = 0; count < paiAux.getPassageiros().size() -1;count++){
                    pai[count] = paiAux.getPassageiros().get(count).getId();
                }

                for (count = 0; count < maeAux.getPassageiros().size()-1;count++){
                    mae[count] = maeAux.getPassageiros().get(count).getId();
                }

                //2 -Realizar o Crossover
                PMX filhos = new PMX(pai, mae);

                //3 - Converter o resultado para uma rota

                //pegar arrays de int criado pelo pmx
                pai = filhos.get_offspring2();
                mae = filhos.get_offspring1();

                MPassageiro mp = new MPassageiro();
                ArrayList<MPassageiro> lstPassageirosPai = new ArrayList<MPassageiro>();
                ArrayList<MPassageiro> lstPassageirosMae = new ArrayList<MPassageiro>();

                for (count = 0; count < pai.length; count ++){
                    lstPassageirosPai.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, pai[count])));

                    lstPassageirosMae.add(lstPassageiros.get(
                            getIndexPassageiroPorId(lstPassageiros, mae[count])));
                }

                //Adiciona o Destino
                lstPassageirosPai.add(passageiroFatec);
                lstPassageirosMae.add(passageiroFatec);

                paiAux.setPassageiros(lstPassageirosPai);
                maeAux.setPassageiros(lstPassageirosMae);

                //Aplicar a mutação
                paiAux = ExchangeMutation(paiAux);
                maeAux = ExchangeMutation(maeAux);

                MRota filhoA = new MRota();
                filhoA.setPassageiros(paiAux.getPassageiros());

                MRota filhoB = new MRota();
                filhoB.setPassageiros(maeAux.getPassageiros());

//                ArrayList<MRota>pop = new ArrayList<MRota>();
//                pop.add(filhoA);
//                pop.add(filhoB);

//                for (int k = 0; k < 2; k++){
//                    //fitness dos passageiros
//                    for (int j = 0; j < pop.get(k).getPassageiros().size()-1; j++){
//                        if(j == pop.get(k).getPassageiros().size() - 1){
//                            indexPass = j;
//                            pop.get(k).getPassageiros().get(indexPass).setFitness(
//                                    calcularFitness(pop.get(k).getPassageiros().get(indexPass).getId(), 2,false)
//                            );
//                        }
//                        else if(j >= 1){
//                            indexPass = j-1;
//                            pop.get(k).getPassageiros().get(indexPass).setFitness(
//                                    calcularFitness(pop.get(k).getPassageiros().get(indexPass).getId(),
//                                            pop.get(k).getPassageiros().get(j).getId(),false)
//                            );
//                        }
//                    }
//
//                    pop.get(k).setFitnessRota(calcularFitnessRota(pop.get(k)));
//                }
//                popAux.add(pop.get(0).getFitnessRota() < paiOriginal.getFitnessRota() ?  pop.get(0): paiOriginal);
//                popAux.add(pop.get(1).getFitnessRota() < maeOriginal.getFitnessRota() ? pop.get(1) : maeOriginal);

                popAux.add(filhoA);
                popAux.add(filhoB);
            }

            //Apagar os velhos membros
            //Inserir novos Membros

            for(int i = 0; i < _SIZEPOPULACAO+1; i++)
            {
                novaPopulacao.add(popAux.get(i));
            }
            popAux = null;

            //aplicar o 2opt
            for (int j = 0; j < novaPopulacao.size()-1; j++){
                novaPopulacao.get(j).setPassageiros(twoOpt(novaPopulacao.get(j).getPassageiros()));
            }

            //Re-Avaliar a populacao
            atualizarValores(novaPopulacao);
            return novaPopulacao;

        }
        catch(Exception e){
            e.getMessage();
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

    public MRota roleta(ArrayList<MRota> pop){
        double numSorteado = (gerarNumeroAleatorio(0,100));
        for(int i = 0; i < pop.size(); i++){
            if(numSorteado >= pop.get(i).getRangeRoleta()[0] && numSorteado <= pop.get(i).getRangeRoleta()[1])
            {
                return pop.get(i);
            }
        }
        //Nunca vai acontecer
        return null;
    }

    public MRota ExchangeMutation(MRota rota){
        if(gerarNumeroAleatorio(0,100)  <= _TAXAMUTACAO){
            int primeiroRandom = gerarNumeroAleatorio(0,_SIZEPOPULACAO -1);
            int segundoRandom = gerarNumeroAleatorio(0,_SIZEPOPULACAO -1);

            MPassageiro mutacao1 = rota.getPassageiros().get(primeiroRandom);
            rota.getPassageiros().set(primeiroRandom, rota.getPassageiros().get(segundoRandom));
            rota.getPassageiros().set(segundoRandom, mutacao1);
        }
        return rota;
    }

    public int gerarNumeroAleatorio(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private ArrayList<MPassageiro> twoOpt(ArrayList<MPassageiro> passageiros){
        boolean modificado = true;
        //se tiver menos de 4 passageiros nao precisa aplicar esse algoritmo
        if(passageiros.size() < 4)
            return passageiros;

        while (modificado) {
            modificado = false;

            for (int i = 0; i < passageiros.size()-1; i++) {
                for (int j = i+2; j+1 < passageiros.size()-1; j++) {

                    double d1 = calcularFitness(passageiros.get(i).getId(),passageiros.get(i+1).getId(), false) +
                            calcularFitness(passageiros.get(j).getId(), passageiros.get(j+1).getId(), false);

                    double d2 = calcularFitness(passageiros.get(i).getId(),passageiros.get(j).getId(), false) +
                            calcularFitness(passageiros.get(i+1).getId(), passageiros.get(j+1).getId(), false);

                    // Ajusta caso a distancia possa ser encurtada
                    if (d2 < d1) {
                        passageiros = trocarPosicoes(passageiros, i+1,j);
                        modificado = true;
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

    private void getPassageirosDistancias(ArrayList<MPassageiro> lstPassageiros, Context context, CRota cr, ProgressDialog mProgressDialog){
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
        rotaModel.getPassageiroDistancia(rq, context, this, params, url, cr, mProgressDialog);
    }

    public void atualizarValores(ArrayList<MRota> pop)
    {
        int indexPass;
        for (int i = 0; i < pop.size(); i++){
            //fitness dos passageiros
            for (int j = 0; j < pop.get(i).getPassageiros().size()-1; j++){
                if(j == pop.get(i).getPassageiros().size() - 1){
                    indexPass = j;
                    pop.get(i).getPassageiros().get(indexPass).setFitness(
                            calcularFitness(pop.get(i).getPassageiros().get(indexPass).getId(), 2,false)
                    );
                }
                else if(j >= 1){
                    indexPass = j-1;
                    pop.get(i).getPassageiros().get(indexPass).setFitness(
                            calcularFitness(pop.get(i).getPassageiros().get(indexPass).getId(),
                                    pop.get(i).getPassageiros().get(j).getId(),false)
                    );
                }
            }
            pop.get(i).setFitnessRota(calcularFitnessRota(pop.get(i)));
        }
        //CalcularFitnessPercent;
        calcularFitnessPercent(pop);

        //CalcularRangeRoleta
        calcularRangeRoleta(pop);
    }

    public void registrarMelhoresRotas(){

    }

    public void calcularTempoDeRota(){

    }

    public void executarRotaSelecionada(){

    }

}
