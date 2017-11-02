package fgv.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;

import fgv.Model.MPassageiro;

import static android.content.ContentValues.TAG;


/**
 * Created by Fernando on 16/01/2017.
 */
public class CCadastrarPassageiro extends Activity implements PlaceSelectionListener{

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText idDestino;
    private EditText nomeResponsavel;
    private EditText telefoneResponsavel;

    private RequestQueue rq;
    private double currentLatitude;
    private double currentLongitude;
    private String logradouro;
    private boolean logradouroSemNumero = false;

    private ArrayList<MPassageiro> lstPassageiros;
    private ArrayAdapter<String> lstPassageirosAdapter;
    private CPassageiro cp;

    @Override
    public void onPlaceSelected(Place place){
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

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_passageiro);
        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.findRidePlaceAutocompleteFragment);

        autocompleteFragment.setOnPlaceSelectedListener(this);
        cp = new CPassageiro();

        btCadastrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                nome = (EditText) findViewById(R.id.edNome);
                cpf = (EditText) findViewById(R.id.edCpf);
                telefone = (EditText) findViewById(R.id.edTelefone);
                idDestino = (EditText) findViewById(R.id.edDestino);
                nomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
                telefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);

                MPassageiro passageiro = new MPassageiro();

                boolean hasErro = false;
                if(!cp.isValidCpf(cpf.getText().toString())){
                    cpf.setError("Digite um cpf válido");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(nome.getText().toString())){
                    nome.setError("Digite o nome");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(telefone.getText().toString())){
                    telefone.setError("Digite o telefone");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(logradouro) || logradouroSemNumero){
                    Toast.makeText(CCadastrarPassageiro.this, "Preencha o endereço com número", Toast.LENGTH_LONG).show();
                    hasErro = true;
                }
                if(TextUtils.isEmpty(nomeResponsavel.getText().toString())){
                    nomeResponsavel.setError("Digite o nome do responsável");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(telefoneResponsavel.getText().toString())){
                    telefoneResponsavel.setError("Digite o telefone do responsável");
                    hasErro = true;
                }
                if(hasErro)
                    return;

                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro);
                passageiro.setIdDestino(1);
                passageiro.setNomeResponsavel(nomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(telefoneResponsavel.getText().toString());

                passageiro.setLatitude(currentLatitude);
                passageiro.setLongitude(currentLongitude);

                try {
                    rq = Volley.newRequestQueue(getBaseContext());
                    cp.inserirPassageiroVolley(rq, getBaseContext(),passageiro);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void displayToast(String msg)
            {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
