package fgv.View;

import android.app.Activity;
import android.content.Intent;
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

public class VPassageiro  extends Activity {

    private static final int READ_BLOCK_SIZE = 100;

    private String nome;
    private String cpf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passageiro);


        nome = findViewById(R.id.edNome).toString();
        cpf = findViewById(R.id.edCpf).toString();


        Button btConsultarPassageiro = (Button) findViewById(R.id.btConsultarPassageiro);

        btConsultarPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                CPassageiro p = new CPassageiro();
                 p.consultarPassageiro(nome);

                Intent iConsultaPassageiro = new Intent(VPassageiro.this, VAtualizarPassageiro.class);
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
