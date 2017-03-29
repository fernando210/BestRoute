package fgv.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.Model.MPassageiro;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class VAtualizarPassageiro  extends Activity {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText logradouro;
    private EditText cidade;
    private EditText estado;
    private EditText bairro;
    private EditText idDestino;
    private EditText nomeResponsavel;
    private EditText telefoneResponsavel;
    private CheckBox ativo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualizar_passageiro);



        Button btAtualizar = (Button) findViewById(R.id.btAtualizar);

        btAtualizar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){



                nome = (EditText) findViewById(R.id.edNome);
                cpf = (EditText) findViewById(R.id.edCpf);
                telefone = (EditText) findViewById(R.id.edTelefone);
                idDestino = (EditText) findViewById(R.id.edDestino);
                logradouro = (EditText) findViewById(R.id.edLogradouro);
                cidade = (EditText) findViewById(R.id.edCidade);
                estado = (EditText) findViewById(R.id.edEstado);
                bairro = (EditText) findViewById(R.id.edBairro);
                nomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
                telefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);
                ativo = (CheckBox)findViewById(R.id.chAtivo);

                MPassageiro passageiro = new MPassageiro();


                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro.getText().toString());
                passageiro.setCidade(cidade.getText().toString());
                passageiro.setEstado(estado.getText().toString());
                passageiro.setBairro(bairro.getText().toString());
                passageiro.setIdDestino(Integer.parseInt(String.valueOf(idDestino.getText())));
                passageiro.setNomeResponsavel(nomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(telefoneResponsavel.getText().toString());

                //passageiro.atualizarPassageiro(passageiro, cpf);
            }
        });

    }



}
