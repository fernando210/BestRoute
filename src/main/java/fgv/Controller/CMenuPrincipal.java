package fgv.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by Vinicius on 28/03/2017.
 */

public class CMenuPrincipal extends Activity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        Button btPassageiro = (Button) findViewById(R.id.btPassageiro);

        btPassageiro.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iPassageiro = new Intent(CMenuPrincipal.this, CPassageiro.class);
                startActivity(iPassageiro);
            }
        });

        Button btRota = (Button) findViewById(R.id.btRota);

        btRota.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent iRota = new Intent(CMenuPrincipal.this, CRota.class);
                //iCadastrarPassageiro.putExtra("lstPassageiros", (new Gson()).toJson(lstPassageiros));
                //iCadastrarPassageiro.putExtra("passageirosAdapter", (new Gson()).toJson(passageirosAdapter));
                startActivity(iRota);
            }
        });
    }

}
