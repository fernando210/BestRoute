package fgv.View;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.DAO.PassageiroAdapter;
import fgv.Model.MPassageiro;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class VPassageiro  extends Activity {

    private static final int READ_BLOCK_SIZE = 100;

    private String nome;
    private EditText edNome;
    private CPassageiro cPassageiro;
    private RequestQueue rq;

    public List<MPassageiro> lstPassageiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passageiro);

        cPassageiro = new CPassageiro();
        rq = Volley.newRequestQueue(getBaseContext());
        try {
            lstPassageiros = cPassageiro.getAllPassageiros(rq,getBaseContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        edNome = (EditText) findViewById(R.id.edNome);
        nome = edNome.getText().toString();

        Button btConsultarPassageiro = (Button) findViewById(R.id.btConsultarPassageiro);

        btConsultarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iConsultaPassageiro = new Intent(VPassageiro.this, VAtualizarPassageiro.class);
                iConsultaPassageiro.putExtra("nome", nome);
                startActivity(iConsultaPassageiro);
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
