/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author LENOVO
 */


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

     private static Connection connection;

   

    private DBConnection() {
    
}
public static Connection getConnection() throws Exception {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gearrentdb",
                "root",
                "Zoom@25@"
        );
    }
    return connection;
 }
}
