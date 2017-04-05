package fgv.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import fgv.Controller.CPassageiro;
import fgv.Controller.R;
import fgv.DAO.PassageiroAdapter;
import fgv.Model.MPassageiro;


/**
 * Created by Fernando on 16/01/2017.
 */
public class VCadastrarPassageiro extends Activity{

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
    private MobileServiceTable<MPassageiro> mToDoTable = null;
    PassageiroAdapter mAdapter;
    MobileServiceClient mClient = null;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_passageiro);

        Button btCadastrar = (Button) findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener(){

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

                MPassageiro passageiro = new MPassageiro();

                passageiro.setNome(nome.getText().toString());
                passageiro.setCpf(cpf.getText().toString());
                passageiro.setTelefone(telefone.getText().toString());
                passageiro.setLogradouro(logradouro.getText().toString());
                passageiro.setCidade(cidade.getText().toString());
                passageiro.setEstado(estado.getText().toString());
                passageiro.setBairro(bairro.getText().toString());
                passageiro.setIdDestino(1);
                passageiro.setNomeResponsavel(nomeResponsavel.getText().toString());
                passageiro.setTelefoneResponsavel(telefoneResponsavel.getText().toString());

                try {
                    mClient = new MobileServiceClient("http://bestrouteapp.azurewebsites.net",VCadastrarPassageiro.this);
                    mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                        @Override
                        public OkHttpClient createOkHttpClient() {
                            OkHttpClient client = new OkHttpClient();
                            client.setReadTimeout(20, TimeUnit.SECONDS);
                            client.setWriteTimeout(20, TimeUnit.SECONDS);
                            return client;
                        }
                    });


                    mToDoTable = mClient.getTable("TB_Passageiro", MPassageiro.class);
                    //mAdapter = new PassageiroAdapter(this, R.layout.atualizar_passageiro);
                    addItem(passageiro);
                    //MPassageiro entity = mToDoTable.insert(passageiro).get();

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

    public void addItem(MPassageiro passageiro) {
        if (mClient == null) {
            return;
        }
        // Create a new item
        final MPassageiro item = passageiro;

        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MPassageiro entity = addItemInTable(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.add(entity);
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        runAsyncTask(task);
    }
    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public MPassageiro addItemInTable(MPassageiro item) throws ExecutionException, InterruptedException {
        MPassageiro entity = mToDoTable.insert(item).get();
        return entity;
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
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
