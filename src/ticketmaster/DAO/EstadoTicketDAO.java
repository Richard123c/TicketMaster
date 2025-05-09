/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.EstadoTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class EstadoTicketDAO {
    
    public static boolean guardarEstado(EstadoTicket estado) {
        String sql = "INSERT INTO estados_ticket (nombre, descripcion, es_final) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado.getNombre());
            stmt.setString(2, estado.getDescripcion());
            stmt.setBoolean(3, estado.isEsFinal());
            int filas = stmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<EstadoTicket> obtenerEstado() {
        List<EstadoTicket> lista = new ArrayList<>();
        String sql = "SELECT * FROM estados_ticket";
        
        try (Connection conn = Conexion.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                EstadoTicket estado = new EstadoTicket(
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("es_final")
                );
                
                //Busca transiciones asociadas
                String sqlTrans = "SELECT siguiente_estado FROM transiciones_estado WHERE estado_origen = ?";
                try (PreparedStatement transStmt = conn.prepareStatement(sqlTrans)) {
                    transStmt.setString(1, estado.getNombre());
                    ResultSet transRs = transStmt.executeQuery();
                    while (transRs.next()) {
                        estado.agregarTransicion(transRs.getString("siguiente_estado"));
                    }
                }
                
                lista.add(estado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static boolean eliminarEstado(String nombre) {
        String sql = "DELETE FROM estados_ticket WHERE nombre = ?";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            int filas = stmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean actualizarEstado(EstadoTicket estado) {
        String sql = "UPDATE estados_ticket SET descripcion = ?, es_final = ? WHERE nombre = ?";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado.getDescripcion());
            stmt.setBoolean(2, estado.isEsFinal());
            stmt.setString(3, estado.getNombre());
            stmt.executeUpdate();
            
            //Limpia trasiciones y reinserta
            String deleteTrans = "DELETE FROM transiciones_estado WHERE estado_origen = ?";
            try (PreparedStatement delStmt = conn.prepareStatement(deleteTrans)) {
                delStmt.setString(1, estado.getNombre());
                delStmt.executeUpdate();
            }
            
            for (String destino : estado.getSiguientesEstados()) {
                String insertTrans = "INSERT INTO transiciones_estado (estado_origen, siguiente_estado) VALUES (?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertTrans)) {
                    insertStmt.setString(1, estado.getNombre());
                    insertStmt.setString(2, destino);
                    insertStmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean estaEnUso(String nombreEstado) {
        String sql = "SELECT COUNT(*) FROM tickets WHERE estado = ?";
        
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombreEstado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
