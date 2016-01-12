package ua.telesens.ostapenko.conf;

import java.sql.*;

/**
 * @author root
 * @since 11.01.16
 */
public class JDBCStatementSelectExample {
    private static final String DB_DRIVER = "mysql.jdbc.driver.MysqlDriver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/forum";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] argv) {

        try {

            selectRecordsFromDbUserTable();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void selectRecordsFromDbUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "SELECT USER_ID, CREATED_BY from DBUSER";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println(selectTableSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {

                String userid = rs.getString("USER_ID");
                String created_by = rs.getString("CREATED_BY");

                System.out.println("userid : " + userid);
                System.out.println("created_by : " + created_by);

            }

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
                    DB_CONNECTION,
                    DB_USER,
                    DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }
}
