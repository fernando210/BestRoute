package fgv.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import fgv.Model.MPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class PassageiroDAO {

    public static final String KEY_CD_DESTINO = "_cdDestino";
    public static final String KEY_CD_PASSAGEIRO = "_cdPassageiro";
    public static final String KEY_CPF_PASSAGEIRO = "_cpfPassageiro";
    public static final String KEY_NOME_PASSAGEIRO = "_nomePassageiro";
    public static final String KEY_EMAIL_RESPONSAVEL = "_emailResponsavel";
    public static final String KEY_END_PASSAGEIRO = "_endPassageiro";
    public static final String KEY_TEL_PASSAGEIRO = "_telPassageiro";
    public static final String KEY_TEL_RESPONSAVEL = "_telResponsavel";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "DbBestRoute";
    private static final String DATABASE_TABLE = "TB_PASSAGEIRO";
    private static final int DATABASE_VERSION = 2;

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public PassageiroDAO(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }
    }

    public PassageiroDAO open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }
    public void inserirPassageiro(MPassageiro passageiro){

    }

    public ArrayList<MPassageiro> selecionarPassageiros(MPassageiro passageiro){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        return passageiros;
    }
    public ArrayList<MPassageiro> consultarPassageiro(){
        ArrayList<MPassageiro> passageiros = new ArrayList<MPassageiro>();

        String query = "SELECT * FROM TABELA_PASSAGEIROS";

        Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_CD_DESTINO, KEY_CD_PASSAGEIRO, KEY_CPF_PASSAGEIRO,
                KEY_EMAIL_RESPONSAVEL, KEY_END_PASSAGEIRO, KEY_NOME_PASSAGEIRO, KEY_TEL_PASSAGEIRO, KEY_TEL_RESPONSAVEL},
                null, null, null, null, null);

        if (c.moveToFirst())
        {
            do {
                MPassageiro passageiro = new MPassageiro();
                passageiro.setCdDestino(c.getInt(c.getColumnIndex("cdDestino")));
                passageiro.setCdPassageiro(c.getInt(c.getColumnIndex("cdPassageiro")));
                passageiro.setCpfPassageiro(c.getString(c.getColumnIndex("cpfPassageiro")));
                passageiro.setEmailResponsavel(c.getString(c.getColumnIndex("EmailResponsavel")));
                passageiro.setEnderecoPassageiro(c.getString(c.getColumnIndex("enderecoPassageiro")));
                passageiro.setNomePassageiro(c.getString(c.getColumnIndex("nomePassageiro")));
                passageiro.setTelefonePassageiro(c.getString(c.getColumnIndex("telefonePassageiro")));
                passageiro.setTelefoneResponsavel(c.getString(c.getColumnIndex("telefoneResponsavel")));
                passageiros.add(passageiro);
            } while (c.moveToNext());
        }
        db.close();
        return passageiros;
    }

    public void alterarPassageiro(MPassageiro passageiro){

    }

    public void excluirPassageiro(MPassageiro passageiro){

    }


}
