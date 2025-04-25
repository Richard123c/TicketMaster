/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.database;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Lenovo
 */
public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/ticketmaster_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456789";
    
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
