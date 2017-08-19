package fgv.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fgv.Model.MPassageiro;

/**
 * Created by Fernando on 16/01/2017.
 */

public class PassageiroDAO {
//
//    public boolean inserirPassageiro(MPassageiro passageiro){
//
//        ConnectionFactory conexao = new ConnectionFactory();
//        ObjetoConexao objConexao = new ObjetoConexao();
//        Connection conn = conexao.dbConnect(objConexao);
//        if (conn != null) {
//            try {
//                Statement statement = conn.createStatement();
//                String queryString = "INSERT INTO TB_PASSAGEIRO VALUES( '" +
//                        passageiro.getCpf() + "', '" +
//                        passageiro.getNome()+ "', '" +
//                        passageiro.getTelefone()+ "', '" +
//                        passageiro.getLogradouro()+ "', '" +
//                        passageiro.getCidade()+ "', '" +
//                        passageiro.getEstado()+ "', '" +
//                        passageiro.getBairro()+ "', '" +
//                        passageiro.getLatitude()+ "', '" +
//                        passageiro.getLongitude()+ "', " +
//                        passageiro.getIdDestino()+ ", '" +
//                        passageiro.getNomeResponsavel()+ "', '" +
//                        passageiro.getTelefoneResponsavel() +
//                        "', Ativo = 1 ";
//
//                statement.execute(queryString);
//                conn.close();
//                return true;
//            }
//            catch (Exception ex){
//                ex.printStackTrace();
//                return false;
//            }
//        }
//            return false;
//    }
//
//    public MPassageiro consultarPassageiro(String nome){
//
//        MPassageiro passageiro = new MPassageiro();
//
//        ConnectionFactory conexao = new ConnectionFactory();
//        ObjetoConexao objConexao = new ObjetoConexao();
//        Connection conn = conexao.dbConnect(objConexao);
//        if (conn != null) {
//            try {
//                Statement statement = conn.createStatement();
//                String queryString = "select nome from TB_PASSAGEIRO where upper(nome) like '%" +  nome + "%'";
//                ResultSet rs;
//                rs = statement.executeQuery(queryString);
//                if (rs.next()) {
//                    passageiro.setId(rs.getInt("Id"));
//                    passageiro.setCpf(rs.getString("Cpf"));
//                    passageiro.setNome(rs.getString("Nome"));
//                    passageiro.setTelefone(rs.getString("Telefone"));
//                    passageiro.setLogradouro(rs.getString("Logradouro"));
//                    passageiro.setCidade(rs.getString("Cidade"));
//                    passageiro.setEstado(rs.getString("Estado"));
//                    passageiro.setBairro(rs.getString("Bairro"));
//                    passageiro.setLatitude(rs.getString("Latitude"));
//                    passageiro.setLongitude(rs.getString("Longitude"));
//                    passageiro.setIdDestino(rs.getInt("IdDestino"));
//                    passageiro.setNomeResponsavel(rs.getString("NomeResponsavel"));
//                    passageiro.setTelefoneResponsavel(rs.getString("TelefoneResponsavel"));
//                    passageiro.setAtivo(rs.getInt("Ativo"));
//                }
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        return passageiro;
//    }
//
//    public boolean atualizarPassageiro(MPassageiro passageiro){
//
//        ConnectionFactory conexao = new ConnectionFactory();
//        ObjetoConexao objConexao = new ObjetoConexao();
//        Connection conn = conexao.dbConnect(objConexao);
//        if (conn != null) {
//            try {
//                Statement statement = conn.createStatement();
//                String queryString = "UPDATE TB_PASSAGEIRO SET Cpf = '" +
//                        passageiro.getCpf() + "', Nome = '" +
//                        passageiro.getNome()+ "', Telefone = '" +
//                        passageiro.getTelefone()+ "', Logradouro = '" +
//                        passageiro.getLogradouro()+ "', Cidade = '" +
//                        passageiro.getCidade()+ "', Estado = '" +
//                        passageiro.getEstado()+ "', Bairro = '" +
//                        passageiro.getBairro()+ "', Latitude = '" +
//                        passageiro.getLatitude()+ "', Longitude = '" +
//                        passageiro.getLongitude()+ "', IdDestino = " +
//                        passageiro.getIdDestino()+ ", NomeResponsavel = '" +
//                        passageiro.getNomeResponsavel()+ "', TelefoneResponsavel = '" +
//                        passageiro.getTelefoneResponsavel()+
//                        "' WHERE Id = " + passageiro.getId();
//
//                statement.execute(queryString);
//                conn.close();
//                return true;
//            }
//            catch (Exception ex){
//                ex.printStackTrace();
//                return false;
//            }
//        }
//        return false;
//    }
//
//    public boolean excluirPassageiro(MPassageiro passageiro){
//
//        ConnectionFactory conexao = new ConnectionFactory();
//        ObjetoConexao objConexao = new ObjetoConexao();
//        Connection conn = conexao.dbConnect(objConexao);
//        if (conn != null) {
//            try {
//                Statement statement = conn.createStatement();
//                String queryString = "UPDATE TB_PASSAGEIRO SET ATIVO = 0 WHERE Id = " + passageiro.getId();
//
//                statement.execute(queryString);
//                conn.close();
//                return true;
//            }
//            catch (Exception ex){
//                ex.printStackTrace();
//                return false;
//            }
//        }
//        return false;
//    }
//
//    public String nomeTabela() {
//        String nome = "";
//        ConnectionFactory conexao = new ConnectionFactory();
//        ObjetoConexao objConexao = new ObjetoConexao();
//        Connection conn = conexao.dbConnect(objConexao);
//        if (conn != null) {
//            try {
//                Statement statement = conn.createStatement();
//                String queryString = "select nome from TB_PASSAGEIRO where id = 1";
//                ResultSet rs;
//                rs = statement.executeQuery(queryString);
//                if (rs.next()) {
//                    nome = rs.getString("nome");
//                }
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return nome;
//    }
//
}
