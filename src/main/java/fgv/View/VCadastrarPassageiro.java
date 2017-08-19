package fgv.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.DAO.PassageiroAdapter;
import fgv.Model.MPassageiro;

import static android.content.ContentValues.TAG;


/**
 * Created by Fernando on 16/01/2017.
 */
public class VCadastrarPassageiro extends Activity implements PlaceSelectionListener{

    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText cidade;
    private EditText estado;
    private EditText bairro;
    private EditText idDestino;
    private EditText nomeResponsavel;
    private EditText telefoneResponsavel;

    private RequestQueue rq;
    private double currentLatitude;
    private double currentLongitude;
    private String logradouro;

    @Override
    public void onPlaceSelected(Place place){
        currentLatitude = place.getLatLng().latitude;
        currentLongitude = place.getLatLng().longitude;
        logradouro = place.getAddress().toString();
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

        btCadastrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                nome = (EditText) findViewById(R.id.edNome);
                cpf = (EditText) findViewById(R.id.edCpf);
                telefone = (EditText) findViewById(R.id.edTelefone);
                idDestino = (EditText) findViewById(R.id.edDestino);
                cidade = (EditText) findViewById(R.id.edCidade);
                estado = (EditText) findViewById(R.id.edEstado);
                bairro = (EditText) findViewById(R.id.edBairro);
                nomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
                telefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);

                MPassageiro passageiro = new MPassageiro();

                //passageiro.setAtivo(1);
                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro);
                passageiro.setCidade(cidade.getText().toString());
                passageiro.setEstado(estado.getText().toString());
                passageiro.setBairro(bairro.getText().toString());
                passageiro.setIdDestino(1);
                passageiro.setNomeResponsavel(nomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(telefoneResponsavel.getText().toString());

                passageiro.setLatitude(currentLatitude);
                passageiro.setLongitude(currentLongitude);

                try {
                    CPassageiro cp = new CPassageiro();
                    rq = Volley.newRequestQueue(getBaseContext());
                    cp.inserirPassageiroVolley(rq, getBaseContext(),passageiro);

                } catch (Exception e) {
                    e.printStackTrace();
                }

//                if(passageiro.inserirPassageiro(passageiro)){
//                    displayToast("Passageiro inserido com sucesso!");
//                }
//                else{
//                    displayToast("erro ao inserir passageiro.");
//                }
            }

            private void displayToast(String msg)
            {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}
