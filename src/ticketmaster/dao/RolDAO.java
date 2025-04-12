/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmaster.dao;

import ticketmaster.model.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class RolDAO {
    public List<Rol> listarRoles() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, FROM roles";
        List<Rol> roles = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Rol rol = new Rol(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"));
                roles.add(rol);
            }
        }
        return roles;
    }
    
    public void asignarPermiso(int rolId, int permisoId) throws SQLException {
        String sql = "INSERT INTO rol_permisos (rol_id, permiso_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rolId);
            pstmt.setInt(2, permisoId);
            pstmt.executeUpdate();
        }
    }
}
