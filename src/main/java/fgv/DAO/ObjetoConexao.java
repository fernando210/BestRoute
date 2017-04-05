package fgv.DAO;

/**
 * Created by Fernando on 21/03/2017.
 */
public class ObjetoConexao {
//    private String db_connect_string = "servertcc.database.windows.net:1433;";
//    private String db_name = "BestRoute";
//    private String db_userid = "ferglevin";
//    private String db_password = "azu@bestRoute";

    private String db_connect_string = "localhost/BestRoute;";
    private String db_name = "BestRoute";
    private String db_userid = "sa";
    private String db_password = "mancha07";

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