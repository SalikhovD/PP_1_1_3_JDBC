package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_HOST = "localHost";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String DB_NAME = "katatraining";

    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        return DriverManager.getConnection(connectionString, DB_USER, DB_PASS);
    }
}
