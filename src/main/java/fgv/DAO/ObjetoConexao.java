package fgv.DAO;

/**
 * Created by Fernando on 21/03/2017.
 */
public class ObjetoConexao {
    private String db_connect_string = "servertcc.database.windows.net;";
    private String db_name = "BestRoute";
    private String db_userid = "ferglevin";
    private String db_password = "azu@bestRoute";

    public String getDb_connect_string() {
        return db_connect_string;
    }

    public String getDb_name() {
        return db_name;
    }

    public String getDb_userid() {
        return db_userid;
    }

    public String getDb_password() {
        return db_password;
    }

}