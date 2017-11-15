package fgv.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import fgv.Model.MPassageiro;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class CPassageiro extends Activity implements Serializable {

    private static final int READ_BLOCK_SIZE = 100;

    private AutoCompleteTextView actvNome;
    private RequestQueue rq;

    public ArrayList<MPassageiro> lstPassageiros = new ArrayList<MPassageiro>();
    public ArrayAdapter<String> passageirosAdapter;

    MPassageiro passageiro = new MPassageiro();
    GoogleAPI gApi;
    private static final int[] weightCpf = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    @Override
    protected void onResume() {
        rq = Volley.newRequestQueue(getBaseContext());
        try {
            passageirosAdapter.clear();
            getAllPassageiros(rq,getBaseContext(), this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        passageirosAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passageiro);

        passageirosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        actvNome = (AutoCompleteTextView) findViewById(R.id.actvNome);
        actvNome.setAdapter(passageirosAdapter);

        final Button btConsultarPassageiro = (Button) findViewById(R.id.btConsultarPassageiro);
        final Button btCalcularMelhorRota = (Button) findViewById(R.id.btCalcularMelhorRota);

        actvNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(actvNome.getText().toString().equals("")){
                    btConsultarPassageiro.setEnabled(false);
                }
                else{
                    btConsultarPassageiro.setEnabled(true);
                }
            }
        });

        btConsultarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iAtualizarPassageiro = new Intent(CPassageiro.this, CAtualizarPassageiro.class);
                iAtualizarPassageiro.putExtra("passageiro",
                        (new Gson()).toJson(getPassageiro(lstPassageiros,
                                actvNome.getText().toString()))
                );
                startActivity(iAtualizarPassageiro);
            }
        });

        btCalcularMelhorRota.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                CRota cr = new CRota();
                cr.calcularMelhorRota(lstPassageiros,null, rq);
            }
        });


        Button btCadastrarPassageiro = (Button) findViewById(R.id.btCadastrarPassageiro);

        btCadastrarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iCadastrarPassageiro = new Intent(CPassageiro.this, CCadastrarPassageiro.class);
                //iCadastrarPassageiro.putExtra("lstPassageiros", (new Gson()).toJson(lstPassageiros));
                //iCadastrarPassageiro.putExtra("passageirosAdapter", (new Gson()).toJson(passageirosAdapter));
                startActivity(iCadastrarPassageiro);
            }
        });
    }

    public void gerarDistancias(){

        String result ="";
        StringWriter st = new StringWriter();
        GoogleAPI gApi = new GoogleAPI(new CPassageiro());

        String query = "";

        //distancias entre passageiros
        for (int i = 0; i < lstPassageiros.size();i++){
            for (int j = 0; j < lstPassageiros.size();j++){
                result = gApi.identificarDistanciaProximoDestino(lstPassageiros.get(i).getLatitude().toString() + "," +
                        lstPassageiros.get(i).getLongitude().toString(),
                        lstPassageiros.get(j).getLatitude().toString() + "," +
                                lstPassageiros.get(j).getLongitude().toString());

                query = "INSERT INTO TB_PASSAGEIRODISTANCIA VALUES(" +
                        lstPassageiros.get(i).getId() +
                        ", " + lstPassageiros.get(j).getId()+ ",null,'" + result + "')";
                st.append(query + "\n");
            }
        }

        //destino
        String destino = "-23.609310,-46.607653";
        for (int i = 0; i < lstPassageiros.size();i++){
            result = gApi.identificarDistanciaProximoDestino(lstPassageiros.get(i).getLatitude().toString() + "," +
                            lstPassageiros.get(i).getLongitude().toString(),destino);

            query = "INSERT INTO TB_PASSAGEIRODISTANCIA VALUES(" +
                    lstPassageiros.get(i).getId() +
                    ", null, 2, '" + result + "')";
            st.append(query + "\n");

        }

    }


    public boolean inserirPassageiroVolley(RequestQueue rq, Context contexto, MPassageiro p){

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("Ativo", "1");
        params.put("nome", p.getNome());
        params.put("cpf", p.getCpf());
        params.put("telefone", p.getTelefone());
        params.put("logradouro", p.getLogradouro());
        params.put("cidade", p.getCidade());
        params.put("estado", p.getEstado());
        params.put("bairro", p.getBairro());
        //params.put("idDestino", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("idMotorista", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("nomeResponsavel", p.getNomeResponsavel());
        params.put("telefoneResponsavel", p.getTelefoneResponsavel());

        params.put("latitude", String.valueOf(p.getLatitude()));
        params.put("longitude", String.valueOf(p.getLongitude()));

        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/InserirPassageiro";

        return passageiro.inserirPassageiroVolley(rq, contexto, params, url);
    }

    public void getAllPassageiros(RequestQueue rq, Context contexto, CPassageiro vp) throws JSONException {
        JSONObject js = new JSONObject();
        js.put("motoristaId",1);

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/GetAllPassageiros?motoristaId=1";
        passageiro.getAllPassageiros(rq,contexto,vp,params,url);
    }

    public MPassageiro getPassageiro(ArrayList<MPassageiro> lst, String texto){
        for (int i = 0; i < lst.size();i++){
            if((lst.get(i).getNome() + " - " + lst.get(i).getCpf()).equals(texto)){
                return lst.get(i);
            }
        }
        return null;
    }

    public ArrayList<MPassageiro> selecionarPassageiros(){
        return new ArrayList<MPassageiro>();
    }
    public MPassageiro consultarPassageiro(String nome){
        return passageiro.consultarPassageiro(nome);
    }

    public void atualizarPassageiro(RequestQueue rq, Context contexto, MPassageiro p){

        Map<String,String> params;
        params = new HashMap<String,String>();

        params.put("Id", String.valueOf(p.getId()));
        params.put("Ativo", String.valueOf(p.getAtivo()));
        params.put("nome", p.getNome());
        params.put("cpf", p.getCpf());
        params.put("telefone", p.getTelefone());
        params.put("logradouro", p.getLogradouro());
        params.put("cidade", p.getCidade());
        params.put("estado", p.getEstado());
        params.put("bairro", p.getBairro());
        //params.put("idDestino", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("idMotorista", "1");//TA CHUMBADO PRA TESTE!!!!!!
        params.put("nomeResponsavel", p.getNomeResponsavel());
        params.put("telefoneResponsavel", p.getTelefoneResponsavel());

        params.put("latitude", String.valueOf(p.getLatitude()));
        params.put("longitude", String.valueOf(p.getLongitude()));

        JSONObject js = new JSONObject(params);

        params = new HashMap<String,String>();
        params.put("json", js.toString());

        String url = "https://bestrouteapi.azurewebsites.net/Api/Mobile/AtualizarPassageiro";
        passageiro.atualizarPassageiro(rq,contexto, params, url);
    }

    public boolean enderecoSemNumero(String endereco){
        String [] enderecoQuebrado = endereco.split(",");
        Pattern p =  Pattern.compile("[0-9]+\\s?-\\s?[A-Za-z]+");

        if(p.matcher(endereco).find(0) == true){
            return false;
        }
        return true;
    }

    public boolean isValidCpf(final String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || cpf.matches(cpf.charAt(0) + "{11}")) return false;

        final Integer digit1 = calcular(cpf.substring(0, 9), weightCpf);
        final Integer digit2 = calcular(cpf.substring(0, 9) + digit1, weightCpf);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    private static int calcular(final String str, final int[] weight) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * weight[weight.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

}
