package fgv.Controller;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.Gson;

import fgv.Model.MPassageiro;

import static android.content.ContentValues.TAG;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class CAtualizarPassageiro extends Activity implements PlaceSelectionListener {

    private EditText edNome;
    private EditText edCpf;
    private EditText edTelefone;
    private Fragment edLogradouro;
    private EditText edDestino;
    private EditText edNomeResponsavel;
    private EditText edTelefoneResponsavel;
    private CheckBox chAtivo;

    private MPassageiro passageiro;
    private CPassageiro cp;
    private RequestQueue rq;
    private double currentLatitude;
    private double currentLongitude;
    private String logradouro;
    private boolean logradouroSemNumero = false;

    @Override
    public void onPlaceSelected(Place place) {
        logradouro = place.getAddress().toString();

        if(cp.enderecoSemNumero(logradouro))
            logradouroSemNumero = true;
        else
            logradouroSemNumero = false;

        currentLatitude = place.getLatLng().latitude;
        currentLongitude = place.getLatLng().longitude;

    }

    @Override
    public void onError(Status status) {
        Log.i(TAG, "Ocorreu um erro: " + status);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualizar_passageiro);

        cp = new CPassageiro();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.edLogradouro);

        autocompleteFragment.setOnPlaceSelectedListener(this);

        cp = new CPassageiro();
        Intent iConsultaPassageiro = getIntent();
        Bundle extras = iConsultaPassageiro.getExtras();
        if(extras != null) {
            passageiro =  (new Gson()).fromJson(extras.get("passageiro").toString(),MPassageiro.class);
        }

        edNome = (EditText) findViewById(R.id.edNome);
        edCpf = (EditText) findViewById(R.id.edCpf);
        edTelefone = (EditText) findViewById(R.id.edTelefone);
        edDestino = (EditText) findViewById(R.id.edDestino);
        edNomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
        edTelefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);
        chAtivo = (CheckBox) findViewById(R.id.chAtivo);

        edNome.setText(passageiro.getNome());
        edCpf.setText(passageiro.getCpf());
        edTelefone.setText(passageiro.getTelefone());
        //edDestino.setText(passageiro.getIdDestino());

        PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.edLogradouro);
        EditText etPlace = (EditText)places.getView().findViewById(R.id.place_autocomplete_search_input);
        etPlace.setText(passageiro.getLogradouro());
        logradouro = passageiro.getLogradouro();
        currentLatitude = passageiro.getLatitude();
        currentLongitude = passageiro.getLongitude();

        edNomeResponsavel.setText(passageiro.getNomeResponsavel());
        edTelefoneResponsavel.setText(passageiro.getTelefoneResponsavel());
        if(passageiro.getAtivo() == 1)
            chAtivo.setChecked(true);
        else
            chAtivo.setChecked(false);

        Button btAtualizar = (Button) findViewById(R.id.btAtualizar);

        btAtualizar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                boolean hasErro = false;
                if(!cp.isValidCpf(edCpf.getText().toString())){
                    edCpf.setError("Digite um cpf");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(edNome.getText().toString())){
                    edNome.setError("Digite o nome");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(edTelefone.getText().toString())){
                    edTelefone.setError("Digite o telefone");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(logradouro) || logradouroSemNumero){
                    Toast.makeText(CAtualizarPassageiro.this, "Preencha o endereço com número", Toast.LENGTH_LONG).show();
                    hasErro = true;
                }
                if(TextUtils.isEmpty(edNomeResponsavel.getText().toString())){
                    edNomeResponsavel.setError("Digite o nome do responsável");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(edTelefoneResponsavel.getText().toString())){
                    edTelefoneResponsavel.setError("Digite o telefone do responsável");
                    hasErro = true;
                }
                if(hasErro)
                    return;

                passageiro.setNome(edNome.getText().toString());
                passageiro.setTelefone(edTelefone.getText().toString());
                passageiro.setLogradouro(logradouro == null ? passageiro.getLogradouro() : logradouro);
    //            passageiro.setIdDestino(Integer.parseInt(String.valueOf(edDestino.getText())));
                passageiro.setNomeResponsavel(edNomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(edTelefoneResponsavel.getText().toString());
                passageiro.setAtivo(chAtivo.isChecked()? 1 : 0);
                passageiro.setLatitude(currentLatitude);
                passageiro.setLongitude(currentLongitude);

                rq = Volley.newRequestQueue(getBaseContext());
                try {
                    cp.atualizarPassageiro(rq, getBaseContext(),passageiro);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
