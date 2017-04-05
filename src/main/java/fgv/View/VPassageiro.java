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

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.DAO.PassageiroAdapter;
import fgv.Model.MPassageiro;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class VPassageiro  extends Activity {

    private static final int READ_BLOCK_SIZE = 100;

    private String nome;
    private EditText edNome;
    private MobileServiceTable<MPassageiro> mToDoTable = null;
    PassageiroAdapter mAdapter;
    MobileServiceClient mClient = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passageiro);
//        try {
//            mClient = new MobileServiceClient("http://bestrouteapp.azurewebsites.net",this);
//            mToDoTable = mClient.getTable("TB_Passageiro", MPassageiro.class);
//            //mAdapter = new PassageiroAdapter(this, R.layout.atualizar_passageiro);
//            List<MPassageiro> results = mToDoTable.execute().get();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    public void showAll(View view) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<MPassageiro> results = mToDoTable.execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAdapter.clear();
                            for (MPassageiro item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        };
        //runAsyncTask(task);
    }
}
