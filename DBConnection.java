import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DBConnection.java
 * Only job: give us a connection to MySQL.
 * JDBC concept shown here: DriverManager.getConnection(url, user, password)
 */
public class DBConnection {

    // jdbc:mysql://host:port/databaseName
    private static final String URL = "jdbc:mysql://localhost:3306/expense_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";   

    // static = we can call it without creating an object: DBConnection.getConnection()
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
