/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.DAO;

import ticketmaster.database.Conexion;
import ticketmaster.model.ParametroSistema;

import java.sql.*;
/**
 *
 * @author Lenovo
 */
public class ParametrosDAO {
    
    public static ParametroSistema obtenerParametros() {
        String sql = "SELECT * FROM parametros_sistema LIMIT 1";
        try (Connection conn = Conexion.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                ParametroSistema p = new ParametroSistema();
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                p.setLogo(rs.getBytes("logo"));
                p.setIdioma(rs.getString("idioma"));
                p.setZonaHoraria(rs.getString("zona_horaria"));
                p.setDiasVencimientoTickets(rs.getInt("vencimiento_tickets"));
                p.setPrioridadAlta(rs.getString("prioridad_alta"));
                p.setPrioridadMedia(rs.getString("prioridad_media"));
                p.setPrioridadBaja(rs.getString("prioridad_baja"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean guardarParametros(ParametroSistema p, String usuario) {
        String deleteSql = "DELETE FROM parametros_sistema";
        String insertSql = """
                           INSERT INTO parametros_sistema
                           (nombre_empresa, logo, idioma, zona_horaria, vencimiento_tickets, prioridad_alta, prioridad_media, prioridad_baja, creado_por, creado_en) 
                           VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW());
                           """;
        
        try (Connection conn = Conexion.conectar();
                Statement delStmt = conn.createStatement();
                PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            
            delStmt.executeUpdate(deleteSql);
            
            stmt.setString(1, p.getNombreEmpresa());
            stmt.setBytes(2, p.getLogo());
            stmt.setString(3, p.getIdioma());
            stmt.setString(4, p.getZonaHoraria());
            stmt.setInt(5, p.getDiasVencimientoTickets());
            stmt.setString(6, p.getPrioridadAlta());
            stmt.setString(7, p.getPrioridadMedia());
            stmt.setString(8, p.getPrioridadBaja());
            stmt.setString(9, usuario);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
