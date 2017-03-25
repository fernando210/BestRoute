package fgv.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.Model.MPassageiro;


/**
 * Created by Fernando on 16/01/2017.
 */
public class VPassageiro extends Activity{

    private EditText id;
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText logradouro;
    private EditText cidade;
    private EditText estado;
    private EditText bairro;
    private Spinner destino;
    private EditText nomeResponsavel;
    private EditText telefoneResponsavel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_passageiro);

        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                nome = (EditText) findViewById(R.id.edNome);
                cpf = (EditText) findViewById(R.id.edCpf);
                telefone = (EditText) findViewById(R.id.edTelefone);
                //destino (Spinner) findViewById(R.id.cbDestino);
                logradouro = (EditText) findViewById(R.id.edLogradouro);
                cidade = (EditText) findViewById(R.id.edCidade);
                estado = (EditText) findViewById(R.id.edEstado);
                bairro = (EditText) findViewById(R.id.edBairro);
                nomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
                telefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);

                MPassageiro passageiro = new MPassageiro();

//                String TIRAESSAPORRADEPOIS = "1";
//                passageiro.setAtivo(Integer.parseInt(TIRAESSAPORRADEPOIS));


                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro.getText().toString());
                passageiro.setCidade(cidade.getText().toString());
                passageiro.setEstado(estado.getText().toString());
                passageiro.setBairro(bairro.getText().toString());
                //passageiro.setDestino();
                passageiro.setNomeResponsavel(nomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(telefoneResponsavel.getText().toString());

                passageiro.inserirPassageiro(passageiro);
            }
        });


        btCadastrar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


                id = (EditText) findViewById(R.id.edId);

                CPassageiro cp = new CPassageiro();
                cp.consultarPassageiro(Integer.parseInt(id.getText().toString()));
            }
        });

        //startActivity(new Intent(this, Janela1.class));

    }

}
