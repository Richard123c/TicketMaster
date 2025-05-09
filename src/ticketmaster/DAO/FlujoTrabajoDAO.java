/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.FlujoTrabajo;

import java.sql.*;
import java.util.*;
/**
 *
 * @author Lenovo
 */
public class FlujoTrabajoDAO {
    
    public static boolean guardarFlujo(FlujoTrabajo flujo) {
        String insertFlujo = "INSERT INTO flujos_trabajo (nombre) VALUES (?)";
        String insertEstado = "INSERT INTO flujo_estados (flujos) VALUES (?, ?)";
        String insertTransicion = "INSERT INTO flujo_transiciones (flujo, origen, destino) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexion.conectar()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement psFlujo = conn.prepareStatement(insertFlujo)) {
                psFlujo.setString(1, flujo.getNombre());
                psFlujo.executeUpdate();
            }
            
            for (String estado : flujo.getEstados()) {
                try(PreparedStatement psEstado = conn.prepareStatement(insertEstado)) {
                    psEstado.setString(1, flujo.getNombre());
                    psEstado.setString(2, estado);
                    psEstado.executeUpdate();
                }
            }
            
            for (Map.Entry<String, List<String>> entry : flujo.getTransiciones().entrySet()) {
                for (String destino : entry.getValue()) {
                    try (PreparedStatement psTrans = conn.prepareStatement(insertTransicion)) {
                        psTrans.setString(1, flujo.getNombre());
                        psTrans.setString(2, entry.getKey());
                        psTrans.setString(3, destino);
                        psTrans.executeUpdate();
                    }
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<FlujoTrabajo> obtenerFlujo() {
        List<FlujoTrabajo> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT nombre FROM flujos_trabajo";
        
        try (Connection conn = Conexion.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                FlujoTrabajo flujo = new FlujoTrabajo(nombre);
                
                try (PreparedStatement psEstados = conn.prepareStatement("SELECT estado FROM flujo_estados WHERE flujo = ?")) {
                    psEstados.setString(1, nombre);
                    ResultSet rsEstados = psEstados.executeQuery();
                    while (rsEstados.next()) {
                        flujo.getEstados().add(rsEstados.getString("estado"));
                    }
                }
                
                try (PreparedStatement psTrans = conn.prepareStatement("SELECT origen, destino FROM flujo_transiciones WHERE flujo = ?")) {
                    psTrans.setString(1, nombre);
                    ResultSet rsTrans = psTrans.executeQuery();
                    while (rsTrans.next()) {
                        flujo.agregarTransicion(rsTrans.getString("origen"), rsTrans.getString("destino"));
                    }
                }
                
                lista.add(flujo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static boolean eliminarFlujo(String nombre) {
        String sql = "DELETE FROM flujos_trabajo WHERE nombre = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean estaEnUso(String nombre) {
        String sql = "SELECT COUNT(*) FROM tickets WHERE flujo = ?";
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
