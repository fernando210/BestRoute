package fgv.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.Model.MPassageiro;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class VAtualizarPassageiro  extends Activity {

    private String nome;
    private EditText edNome;
    private EditText edCpf;
    private EditText edTelefone;
    private EditText edLogradouro;
    private EditText edCidade;
    private EditText edEstado;
    private EditText edBairro;
    private EditText edDestino;
    private EditText edNomeResponsavel;
    private EditText edTelefoneResponsavel;
    private CheckBox edAtivo;
    private int ativo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualizar_passageiro);

        Intent iConsultaPassageiro= getIntent();
        Bundle extras = iConsultaPassageiro.getExtras();
        if(extras != null) {
            nome = extras.getString("nome");
        }

        MPassageiro mp = new MPassageiro();
        CPassageiro p = new CPassageiro();
        mp = p.consultarPassageiro(nome);
        edNome.setText(mp.getNome());
        edCpf.setText(mp.getCpf());
        edTelefone.setText(mp.getTelefone());
        edDestino.setText(mp.getIdDestino());
        edLogradouro.setText(mp.getLogradouro());
        edCidade.setText(mp.getCidade());
        edEstado.setText(mp.getEstado());
        edBairro.setText(mp.getBairro());
        edNomeResponsavel.setText(mp.getNomeResponsavel());
        edTelefoneResponsavel.setText(mp.getTelefoneResponsavel());
        if(mp.getAtivo() == 1)
            edAtivo.setChecked(true);
        else
            edAtivo.setChecked(false);


        Button btAtualizar = (Button) findViewById(R.id.btAtualizar);

        btAtualizar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


            MPassageiro passageiro = new MPassageiro();


            passageiro.setNome(edNome.getText().toString());
            passageiro.setCpf(edCpf.getText().toString());
            passageiro.setTelefone(edTelefone.getText().toString());
            passageiro.setLogradouro(edLogradouro.getText().toString());
            passageiro.setCidade(edCidade.getText().toString());
            passageiro.setEstado(edEstado.getText().toString());
            passageiro.setBairro(edBairro.getText().toString());
            passageiro.setIdDestino(Integer.parseInt(String.valueOf(edDestino.getText())));
            passageiro.setNomeResponsavel(edNomeResponsavel.getText().toString());
            passageiro.setTelefoneResponsavel(edTelefoneResponsavel.getText().toString());

            //passageiro.atualizarPassageiro(passageiro, cpf);
            }
        });

    }



}
