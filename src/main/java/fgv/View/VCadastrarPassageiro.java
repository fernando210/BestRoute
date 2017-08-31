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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
    private EditText idDestino;
    private EditText nomeResponsavel;
    private EditText telefoneResponsavel;

    private RequestQueue rq;
    private double currentLatitude;
    private double currentLongitude;
    private String logradouro;
    private static final int[] weightCpf = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private ArrayList<MPassageiro> lstPassageiros;
    private ArrayAdapter<String> lstPassageirosAdapter;

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
                nomeResponsavel = (EditText) findViewById(R.id.edNomeResponsavel);
                telefoneResponsavel = (EditText) findViewById(R.id.edTelefoneResponsavel);

                MPassageiro passageiro = new MPassageiro();

                boolean hasErro = false;
                if(!isValidCpf(cpf.getText().toString())){
                    cpf.setError("Digite um cpf válido");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(nome.getText().toString())){
                    nome.setError("Digite o nome");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(telefone.getText().toString())){
                    telefone.setError("Digite o telefone");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(logradouro)){
                    Toast.makeText(VCadastrarPassageiro.this, "Preencha o endereço", Toast.LENGTH_LONG).show();
                    hasErro = true;
                }
                if(TextUtils.isEmpty(nomeResponsavel.getText().toString())){
                    nomeResponsavel.setError("Digite o nome do responsável");
                    hasErro = true;
                }
                if(TextUtils.isEmpty(telefoneResponsavel.getText().toString())){
                    telefoneResponsavel.setError("Digite o telefone do responsável");
                    hasErro = true;
                }
                if(hasErro)
                    return;

                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro);
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
            }

            private void displayToast(String msg)
            {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValidCpf(final String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || cpf.matches(cpf.charAt(0) + "{11}")) return false;

        final Integer digit1 = calcular(cpf.substring(0, 9), weightCpf);
        final Integer digit2 = calcular(cpf.substring(0, 9) + digit1, weightCpf);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    private static int calcular(final String str, final int[] weight) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * weight[weight.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
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
