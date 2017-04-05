package fgv.DAO;

/**
 * Created by Fernando on 16/01/2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import android.annotation.SuppressLint;
import android.os.StrictMode;

public class ConnectionFactory {
    @SuppressLint("NewApi")
    public Connection dbConnect(ObjetoConexao objConexao) {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connectionUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            connectionUrl = "jdbc:jtds:sqlserver://" +
//                    objConexao.getDb_connect_string() +
//                    "database=" + objConexao.getDb_name()+ "user="
//                    +objConexao.getDb_userid() +
//                    "@servertcc;password=" +  objConexao.getDb_password()+ ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=120";

//            connectionUrl = "jdbc:jtds:sqlserver://" +
//                    objConexao.getDb_connect_string() +
//                    "database=" + objConexao.getDb_name()+ ";user="
//                    +objConexao.getDb_userid() +
//                    ";password=" +  objConexao.getDb_password();
//
//                    conn = DriverManager.getConnection(connectionUrl);

            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://127.0.0.1:1433/BestRoute;user=sa;password=mancha07");
            //"jdbc:jtds:sqlserver://127.0.0.1:1433;instanceName=SQLEXPRESS;databaseName=BestRoute;user=sa;password=mancha07"
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
