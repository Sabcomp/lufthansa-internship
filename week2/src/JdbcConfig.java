import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {

    // database connection constants
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "internship";
    private static final String USER="root";
    private static final String PASSWORD="";
    private static final String URL="jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    // create and return a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
