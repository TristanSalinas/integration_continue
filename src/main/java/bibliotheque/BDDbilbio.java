package bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDDbilbio {
    public static Connection getConnection() throws SQLException {
        String host = System.getProperty("mysql.host", "localhost");
        String port = System.getProperty("mysql.port", "3306");
        String database = System.getProperty("mysql.database", "bibliotheque");
        String user = System.getProperty("mysql.user", "root");
        String password = System.getProperty("mysql.password", "password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        return DriverManager.getConnection(url, user, password);
    }
}
