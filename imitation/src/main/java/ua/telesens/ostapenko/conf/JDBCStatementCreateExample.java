package ua.telesens.ostapenko.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author root
 * @since 11.01.16
 */
public class JDBCStatementCreateExample {

    private static final String DB_DRIVER = "mysql.jdbc.driver.MysqlDriver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/forum";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    public static void main(String[] argv) {

        try {

            createDbUserTable();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void createDbUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String createTableSQL = "CREATE TABLE DBUSER2("
                + "USER_ID INT(5) NOT NULL, "
                + "USERNAME VARCHAR(20) NOT NULL, "
                + "CREATED_BY VARCHAR(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
                + ")";

        try {
            dbConnection = getDBConnection();
            // create statment
            statement = dbConnection.createStatement();

            System.out.println(createTableSQL);
            // execute the SQL stetement
            statement.execute(createTableSQL);


            System.out.println("Table \"dbuser\" is created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName("dslkjfl");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }
}
