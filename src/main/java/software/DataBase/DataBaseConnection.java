package software.DataBase;


import software.FirstPass;
import software.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DataBaseConnection {
    private static DataBaseConnection singleInstance = null;
    private Connection connection;

    private DataBaseConnection() {
        String className = test.class.getName().replace('.', '/');
        String classJar = Objects.requireNonNull(FirstPass.class.getResource("/" + className + ".class")).toString();
        String databaseURL = "";
        if (classJar.startsWith("jar:")) {
            databaseURL = "jdbc:sqlite:./firstpass.db";
        } else {
            databaseURL = "jdbc:sqlite:" + FirstPass.class.getResource("firstpass.db");
        }
        try {
            connection = DriverManager.getConnection(databaseURL);
            System.out.println("Connexion OK !");
        } catch (SQLException ex) {
            System.out.println("Connexion ERROR !");
            ex.printStackTrace();
        }
    }

    public static DataBaseConnection getInstance() {
        if (singleInstance == null)
            singleInstance = new DataBaseConnection();
        return singleInstance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Closing connexion !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        singleInstance = null;
    }
}
