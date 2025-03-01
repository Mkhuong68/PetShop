/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tvhun
 */
public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://MYDEVICE:1433;databaseName=PetShop;user=sa;password=123;encrypt=true;trustServerCertificate=true";
          connection = DriverManager.getConnection(url);
            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found!", e);
            }
        }
        return connection;
    }
}
