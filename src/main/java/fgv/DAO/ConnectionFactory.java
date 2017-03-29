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
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:sqlserver://" +
                    objConexao.getDb_connect_string() + ":1433;" +
                    "database=" + objConexao.getDb_name()+ ";user="
                    +objConexao.getDb_userid() +
                    "@servertcc;password=" +  objConexao.getDb_password()+ ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(connectionUrl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
