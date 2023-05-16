package c195Project.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This is the connection class to be able to communicate with the data base - this was class relates to the WGU site to utilize
 * as the connection class for the entirety of the project
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL - CHANGE BACK TO 'SERVER' FOR THE VM
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    private static Connection connection;  // Connection Interface

    /**
     * opens the connection to the data base and outputs the message that the connection is successful
     */
    private static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * closes the connection to the DB and outputs message that it is no longer connected
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * this get connection is called when a query that will be executed to create update and delete info on the data base
     * @return
     */
    public static Connection getConnection() {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }
}
