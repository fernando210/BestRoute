package fgv.View;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.DAO.PassageiroAdapter;
import fgv.Model.MPassageiro;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class VPassageiro  extends Activity {

    private static final int READ_BLOCK_SIZE = 100;

    private AutoCompleteTextView actvNome;
    private CPassageiro cPassageiro;
    private RequestQueue rq;

    public ArrayList<MPassageiro> lstPassageiros = new ArrayList<MPassageiro>();
    public ArrayAdapter<String> passageirosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passageiro);

        cPassageiro = new CPassageiro();
        rq = Volley.newRequestQueue(getBaseContext());
        try {
            cPassageiro.getAllPassageiros(rq,getBaseContext(), this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        passageirosAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        actvNome = (AutoCompleteTextView) findViewById(R.id.actvNome);
        actvNome.setAdapter(passageirosAdapter);

        Button btConsultarPassageiro = (Button) findViewById(R.id.btConsultarPassageiro);

        btConsultarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iAtualizarPassageiro = new Intent(VPassageiro.this, VAtualizarPassageiro.class);
                iAtualizarPassageiro.putExtra("passageiro",
                        (new Gson()).toJson(cPassageiro.getPassageiro(lstPassageiros,
                                actvNome.getText().toString()))
                );
                startActivity(iAtualizarPassageiro);
            }
        });

        Button btCadastrarPassageiro = (Button) findViewById(R.id.btCadastrarPassageiro);

        btCadastrarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iCadastrarPassageiro = new Intent(VPassageiro.this, VCadastrarPassageiro.class);
                startActivity(iCadastrarPassageiro);
            }
        });
    }

}
