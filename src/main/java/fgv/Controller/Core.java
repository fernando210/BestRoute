package fgv.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fgv.Model.MPassageiro;

public class Core extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_core);
        CPassageiro pas = new CPassageiro();
        MPassageiro mPas =  pas.consultarPassageiro(1);
        String teste = mPas.getId() + " - " + mPas.getId();

    }
}
