package ua.telesens.ostapenko.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author root
 * @since 11.01.16
 */
public class JDBCStatementInsertExample {

    private static final String DB_DRIVER = "mysql.jdbc.driver.MysqlDriver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/forum";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static void main(String[] argv) {

        try {

            insertRecordIntoDbUserTable();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void insertRecordIntoDbUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;
        String date = getCurrentTimeStamp();
        System.out.println(date);
        String insertTableSQL = "INSERT INTO DBUSER"
                + "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
                + "('" + 45 + "','greg','system', '" + date + "')";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println(insertTableSQL);

            // execute insert SQL stetement
            statement.executeUpdate(insertTableSQL);

            System.out.println("Record is inserted into DBUSER table!");

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

            Class.forName(DB_DRIVER);

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

    private static String getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return dateFormat.format(today.getTime());

    }
}
